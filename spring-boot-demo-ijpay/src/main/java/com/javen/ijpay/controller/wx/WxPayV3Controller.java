package com.javen.ijpay.controller.wx;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.RequestMethod;
import com.ijpay.core.kit.AesUtil;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.PayKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.core.utils.DateTimeZoneUtil;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxApiType;
import com.ijpay.wxpay.enums.WxDomain;
import com.ijpay.wxpay.model.v3.Amount;
import com.ijpay.wxpay.model.v3.Payer;
import com.ijpay.wxpay.model.v3.UnifiedOrderModel;
import com.javen.ijpay.bean.WxPayV3Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * <p>IJPay 让支付触手可及，封装了微信支付、支付宝支付、银联支付常用的支付方式以及各种常用的接口。</p>
 *
 * <p>不依赖任何第三方 mvc 框架，仅仅作为工具使用简单快速完成支付模块的开发，可轻松嵌入到任何系统里。 </p>
 *
 * <p>IJPay 交流群: 723992875</p>
 *
 * <p>Node.js 版: https://gitee.com/javen205/TNWX</p>
 *
 * <p>微信支付 v3 接口示例</p>
 *
 * @author Javen
 */
@Controller
@RequestMapping("/v3")
public class WxPayV3Controller {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    WxPayV3Bean wxPayV3Bean;

    String serialNo;
    String platSerialNo;


    @RequestMapping("")
    @ResponseBody
    public String index() {
        log.info(wxPayV3Bean.toString());
        return ("欢迎使用 IJPay 中的微信支付 Api-v3 -By Javen  <br/><br>  交流群：723992875");
    }

    @RequestMapping("/getSerialNumber")
    @ResponseBody
    public String serialNumber() {
        return getSerialNumber();
    }

    @RequestMapping("/getPlatSerialNumber")
    @ResponseBody
    public String platSerialNumber() {
        return getPlatSerialNumber();
    }

    private String getSerialNumber() {
        if (StrUtil.isEmpty(serialNo)) {
            // 获取证书序列号
            X509Certificate certificate = PayKit.getCertificate(FileUtil.getInputStream(wxPayV3Bean.getCertPath()));
            serialNo = certificate.getSerialNumber().toString(16).toUpperCase();

            System.out.println("输出证书信息:\n" + certificate.toString());
            // 输出关键信息，截取部分并进行标记
            System.out.println("证书序列号:" + certificate.getSerialNumber().toString(16));
            System.out.println("版本号:" + certificate.getVersion());
            System.out.println("签发者：" + certificate.getIssuerDN());
            System.out.println("有效起始日期：" + certificate.getNotBefore());
            System.out.println("有效终止日期：" + certificate.getNotAfter());
            System.out.println("主体名：" + certificate.getSubjectDN());
            System.out.println("签名算法：" + certificate.getSigAlgName());
            System.out.println("签名：" + Arrays.toString(certificate.getSignature()));
        }
        System.out.println("serialNo:" + serialNo);
        return serialNo;
    }

    private String getPlatSerialNumber() {
        if (StrUtil.isEmpty(platSerialNo)) {
            // 获取平台证书序列号
            X509Certificate certificate = PayKit.getCertificate(FileUtil.getInputStream(wxPayV3Bean.getPlatformCertPath()));
            platSerialNo = certificate.getSerialNumber().toString(16).toUpperCase();
        }
        System.out.println("platSerialNo:" + platSerialNo);
        return platSerialNo;
    }

    private String savePlatformCert(String associatedData, String nonce, String cipherText, String certPath) {
        try {
            AesUtil aesUtil = new AesUtil(wxPayV3Bean.getApiKey3().getBytes(StandardCharsets.UTF_8));
            // 平台证书密文解密
            // encrypt_certificate 中的  associated_data nonce  ciphertext
            String publicKey = aesUtil.decryptToString(
                    associatedData.getBytes(StandardCharsets.UTF_8),
                    nonce.getBytes(StandardCharsets.UTF_8),
                    cipherText
            );
            // 保存证书
            FileWriter writer = new FileWriter(certPath);
            writer.write(publicKey);
            // 获取平台证书序列号
            X509Certificate certificate = PayKit.getCertificate(new ByteArrayInputStream(publicKey.getBytes()));
            return certificate.getSerialNumber().toString(16).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/platformCert")
    @ResponseBody
    public String platformCert() {
        try {
            String associatedData = "certificate";
            String nonce = "80d28946a64a";
            String cipherText = "DwAqW4+4TeUaOEylfKEXhw+XqGh/YTRhUmLw/tBfQ5nM9DZ9d+9aGEghycwV1jwo52vXb/t6ueBvBRHRIW5JgDRcXmTHw9IMTrIK6HxTt2qiaGTWJU9whsF+GGeQdA7gBCHZm3AJUwrzerAGW1mclXBTvXqaCl6haE7AOHJ2g4RtQThi3nxOI63/yc3WaiAlSR22GuCpy6wJBfljBq5Bx2xXDZXlF2TNbDIeodiEnJEG2m9eBWKuvKPyUPyClRXG1fdOkKnCZZ6u+ipb4IJx28n3MmhEtuc2heqqlFUbeONaRpXv6KOZmH/IdEL6nqNDP2D7cXutNVCi0TtSfC7ojnO/+PKRu3MGO2Z9q3zyZXmkWHCSms/C3ACatPUKHIK+92MxjSQDc1E/8faghTc9bDgn8cqWpVKcL3GHK+RfuYKiMcdSkUDJyMJOwEXMYNUdseQMJ3gL4pfxuQu6QrVvJ17q3ZjzkexkPNU4PNSlIBJg+KX61cyBTBumaHy/EbHiP9V2GeM729a0h5UYYJVedSo1guIGjMZ4tA3WgwQrlpp3VAMKEBLRJMcnHd4pH5YQ/4hiUlHGEHttWtnxKFwnJ6jHr3OmFLV1FiUUOZEDAqR0U1KhtGjOffnmB9tymWF8FwRNiH2Tee/cCDBaHhNtfPI5129SrlSR7bZc+h7uzz9z+1OOkNrWHzAoWEe3XVGKAywpn5HGbcL+9nsEVZRJLvV7aOxAZBkxhg8H5Fjt1ioTJL+qXgRzse1BX1iiwfCR0fzEWT9ldDTDW0Y1b3tb419MhdmTQB5FsMXYOzqp5h+Tz1FwEGsa6TJsmdjJQSNz+7qPSg5D6C2gc9/6PkysSu/6XfsWXD7cQkuZ+TJ/Xb6Q1Uu7ZB90SauA8uPQUIchW5zQ6UfK5dwMkOuEcE/141/Aw2rlDqjtsE17u1dQ6TCax/ZQTDQ2MDUaBPEaDIMPcgL7fCeijoRgovkBY92m86leZvQ+HVbxlFx5CoPhz4a81kt9XJuEYOztSIKlm7QNfW0BvSUhLmxDNCjcxqwyydtKbLzA+EBb2gG4ORiH8IOTbV0+G4S6BqetU7RrO+/nKt21nXVqXUmdkhkBakLN8FUcHygyWnVxbA7OI2RGnJJUnxqHd3kTbzD5Wxco4JIQsTOV6KtO5c960oVYUARZIP1SdQhqwELm27AktEN7kzg/ew/blnTys/eauGyw78XCROb9F1wbZBToUZ7L+8/m/2tyyyqNid+sC9fYqJoIOGfFOe6COWzTI/XPytCHwgHeUxmgk7NYfU0ukR223RPUOym6kLzSMMBKCivnNg68tbLRJHEOpQTXFBaFFHt2qpceJpJgw5sKFqx3eQnIFuyvA1i8s2zKLhULZio9hpsDJQREOcNeHVjEZazdCGnbe3Vjg7uqOoVHdE/YbNzJNQEsB3/erYJB+eGzyFwFmdAHenG5RE6FhCutjszwRiSvW9F7wvRK36gm7NnVJZkvlbGwh0UHr0pbcrOmxT81xtNSvMzT0VZNLTUX2ur3AGLwi2ej8BIC0H41nw4ToxTnwtFR1Xy55+pUiwpB7JzraA08dCXdFdtZ72Tw/dNBy5h1P7EtQYiKzXp6rndfOEWgNOsan7e1XRpCnX7xoAkdPvy40OuQ5gNbDKry5gVDEZhmEk/WRuGGaX06CG9m7NfErUsnQYrDJVjXWKYuARd9R7W0aa5nUXqz/Pjul/LAatJgWhZgFBGXhNr9iAoade/0FPpBj0QWa8SWqKYKiOqXqhfhppUq35FIa0a1Vvxcn3E38XYpVZVTDEXcEcD0RLCu/ezdOa6vRcB7hjgXFIRZQAka0aXnQxwOZwE2Rt3yWXqc+Q1ah2oOrg8Lg3ETc644X9QP4FxOtDwz/A==";
            return savePlatformCert(associatedData, nonce, cipherText, wxPayV3Bean.getPlatformCertPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/get")
    @ResponseBody
    public String v3Get() {
        // 获取平台证书列表
        try {
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethod.GET,
                    WxDomain.CHINA.toString(),
                    WxApiType.GET_CERTIFICATES.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    ""
            );

            String timestamp = response.getHeader("Wechatpay-Timestamp");
            String nonceStr = response.getHeader("Wechatpay-Nonce");
            String serialNumber = response.getHeader("Wechatpay-Serial");
            String signature = response.getHeader("Wechatpay-Signature");

            log.info("timestamp {} nonceStr {} signature {}",timestamp,nonceStr,signature);

            String body = response.getBody();
            int status = response.getStatus();

            log.info("serialNumber: {}", serialNumber);
            log.info("status: {}", status);
            log.info("body: {}", body);
            int isOk = 200;
            if (status == isOk) {
                JSONObject jsonObject = JSONUtil.parseObj(body);
                JSONArray dataArray = jsonObject.getJSONArray("data");
                // 默认认为只有一个平台证书
                JSONObject encryptObject = dataArray.getJSONObject(0);
                JSONObject encryptCertificate = encryptObject.getJSONObject("encrypt_certificate");
                String associatedData = encryptCertificate.getStr("associated_data");
                String cipherText = encryptCertificate.getStr("ciphertext");
                String nonce = encryptCertificate.getStr("nonce");
                String serialNo = encryptObject.getStr("serial_no");
                final String platSerialNo = savePlatformCert(associatedData, nonce, cipherText, wxPayV3Bean.getPlatformCertPath());
                log.info("平台证书序列号: {} serialNo: {}", platSerialNo, serialNo);
            }
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, wxPayV3Bean.getPlatformCertPath());
            System.out.println("verifySignature:" + verifySignature);
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/nativePay")
    @ResponseBody
    public String nativePay() {
        try {
            String timeExpire = DateTimeZoneUtil.dateToTimeZone(System.currentTimeMillis() + 1000 * 60 * 3);
            UnifiedOrderModel unifiedOrderModel = new UnifiedOrderModel()
                    .setAppid(wxPayV3Bean.getAppId())
                    .setMchid(wxPayV3Bean.getMchId())
                    .setDescription("IJPay 让支付触手可及")
                    .setOut_trade_no(PayKit.generateStr())
                    .setTime_expire(timeExpire)
                    .setAttach("微信系开发脚手架 https://gitee.com/javen205/TNWX")
                    .setNotify_url(wxPayV3Bean.getDomain().concat("/v3/payNotify"))
                    .setAmount(new Amount().setTotal(1));

            log.info("统一下单参数 {}", JSONUtil.toJsonStr(unifiedOrderModel));
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethod.POST,
                    WxDomain.CHINA.toString(),
                    WxApiType.NATIVE_PAY.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    JSONUtil.toJsonStr(unifiedOrderModel)
            );
            log.info("统一下单响应 {}", response);
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, wxPayV3Bean.getPlatformCertPath());
            log.info("verifySignature: {}", verifySignature);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/jsApiPay")
    @ResponseBody
    public String jsApiPay(@RequestParam(value = "openId", required = false, defaultValue = "o-_-itxuXeGW3O1cxJ7FXNmq8Wf8") String openId) {
        try {
            String timeExpire = DateTimeZoneUtil.dateToTimeZone(System.currentTimeMillis() + 1000 * 60 * 3);
            UnifiedOrderModel unifiedOrderModel = new UnifiedOrderModel()
                    .setAppid(wxPayV3Bean.getAppId())
                    .setMchid(wxPayV3Bean.getMchId())
                    .setDescription("IJPay 让支付触手可及")
                    .setOut_trade_no(PayKit.generateStr())
                    .setTime_expire(timeExpire)
                    .setAttach("微信系开发脚手架 https://gitee.com/javen205/TNWX")
                    .setNotify_url(wxPayV3Bean.getDomain().concat("/v3/payNotify"))
                    .setAmount(new Amount().setTotal(1))
                    .setPayer(new Payer().setOpenid(openId));

            log.info("统一下单参数 {}", JSONUtil.toJsonStr(unifiedOrderModel));
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethod.POST,
                    WxDomain.CHINA.toString(),
                    WxApiType.JS_API_PAY.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    JSONUtil.toJsonStr(unifiedOrderModel)
            );
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, wxPayV3Bean.getPlatformCertPath());
            log.info("verifySignature: {}", verifySignature);
            log.info("统一下单响应 {}", response);

            if (verifySignature) {
                String body = response.getBody();
                JSONObject jsonObject = JSONUtil.parseObj(body);
                String prepayId = jsonObject.getStr("prepay_id");
                Map<String, String> map = WxPayKit.jsApiCreateSign(wxPayV3Bean.getAppId(), prepayId, wxPayV3Bean.getKeyPath());
                log.info("唤起支付参数:{}", map);
                return JSONUtil.toJsonStr(map);
            }
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/put")
    @ResponseBody
    public String put() {
        try {
            Map<String, String> params = new HashMap<>(16);
            params.put("url", "https://gitee.com/javen205/IJPay");

            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethod.PUT,
                    WxDomain.CHINA.toString(),
                    WxApiType.MERCHANT_SERVICE_COMPLAINTS_NOTIFICATIONS.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    JSONUtil.toJsonStr(params)
            );
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, wxPayV3Bean.getPlatformCertPath());
            log.info("verifySignature: {}", verifySignature);
            log.info("响应 {}", response);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("/getParams")
    @ResponseBody
    public String payScoreUserServiceState() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("service_id", "500001");
            params.put("appid", "wxd678efh567hg6787");
            params.put("openid", "oUpF8uMuAJO_M2pxb1Q9zNjWeS6o");

            IJPayHttpResponse result = WxPayApi.v3(
                    RequestMethod.GET,
                    WxDomain.CHINA.toString(),
                    WxApiType.PAY_SCORE_USER_SERVICE_STATE.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    params
            );
            System.out.println(result);
            return JSONUtil.toJsonStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String v3Delete() {
        // 创建/查询/更新/删除投诉通知回调
        try {
            HashMap<String, String> hashMap = new HashMap<>(12);
            hashMap.put("url", "https://qq.com");
            IJPayHttpResponse result = WxPayApi.v3(
                    RequestMethod.POST,
                    WxDomain.CHINA.toString(),
                    WxApiType.MERCHANT_SERVICE_COMPLAINTS_NOTIFICATIONS.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    JSONUtil.toJsonStr(hashMap)
            );
            System.out.println(result);

            result = WxPayApi.v3(
                    RequestMethod.DELETE,
                    WxDomain.CHINA.toString(),
                    WxApiType.MERCHANT_SERVICE_COMPLAINTS_NOTIFICATIONS.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    ""
            );
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(result, wxPayV3Bean.getPlatformCertPath());
            System.out.println("verifySignature:" + verifySignature);
            // 如果返回的为 204 表示删除成功
            System.out.println(result);
            return JSONUtil.toJsonStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public String v3Upload() {
        // v3 接口上传文件
        try {
            String filePath = "/Users/Javen/Documents/pic/cat.png";

            File file = FileUtil.newFile(filePath);
            String sha256 = SecureUtil.sha256(file);

            HashMap<Object, Object> map = new HashMap<>(16);
            map.put("filename", file.getName());
            map.put("sha256", sha256);
            String body = JSONUtil.toJsonStr(map);

            System.out.println(body);

            IJPayHttpResponse result = WxPayApi.v3(
                    WxDomain.CHINA.toString(),
                    WxApiType.MERCHANT_UPLOAD_MEDIA.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    body,
                    file
            );
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(result, wxPayV3Bean.getPlatformCertPath());
            System.out.println("verifySignature:" + verifySignature);
            System.out.println(result);
            return JSONUtil.toJsonStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/post")
    @ResponseBody
    public String payGiftActivity() {
        // 支付有礼-终止活动
        try {
            String urlSuffix = String.format(WxApiType.PAY_GIFT_ACTIVITY_TERMINATE.toString(), "10028001");
            System.out.println(urlSuffix);
            IJPayHttpResponse result = WxPayApi.v3(
                    RequestMethod.POST,
                    WxDomain.CHINA.toString(),
                    urlSuffix,
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    ""
            );
            System.out.println(result);
            return JSONUtil.toJsonStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/sensitive")
    @ResponseBody
    public String sensitive() {
        // 带有敏感信息接口
        try {
            String body = "处理请求参数";

            IJPayHttpResponse result = WxPayApi.v3(
                    RequestMethod.POST,
                    WxDomain.CHINA.toString(),
                    WxApiType.APPLY_4_SUB.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    getPlatSerialNumber(),
                    wxPayV3Bean.getKeyPath(),
                    body
            );
            System.out.println(result);
            return JSONUtil.toJsonStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/cipher")
    @ResponseBody
    public String cipher() {
        try {
            // 敏感信息加密
            X509Certificate certificate = PayKit.getCertificate(FileUtil.getInputStream(wxPayV3Bean.getPlatformCertPath()));
            String encrypt = PayKit.rsaEncryptOAEP("IJPay", certificate);
            System.out.println(encrypt);
            // 敏感信息解密
            String encryptStr = "";
            PrivateKey privateKey = PayKit.getPrivateKey(wxPayV3Bean.getKeyPath());
            String decrypt = PayKit.rsaDecryptOAEP(encryptStr, privateKey);
            System.out.println(decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 申请交易账单
     *
     * @param billDate 2020-06-14 当天账单后一天出，不然会出现「账单日期格式不正确」错误
     * @return 交易账单下载地址
     */
    @RequestMapping("/tradeBill")
    @ResponseBody
    public String tradeBill(@RequestParam(value = "billDate", required = false) String billDate) {
        try {
            if (StrUtil.isEmpty(billDate)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE, -1);
                billDate = DateUtil.format(calendar.getTime(), "YYYY-MM-dd");
            }
            Map<String, String> params = new HashMap<>(12);
            params.put("bill_date", billDate);
            params.put("bill_type", "ALL");
            params.put("tar_type", "GZIP");

            IJPayHttpResponse result = WxPayApi.v3(
                    RequestMethod.GET,
                    WxDomain.CHINA.toString(),
                    WxApiType.TRADE_BILL.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    params
            );
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(result, wxPayV3Bean.getPlatformCertPath());
            log.info("verifySignature: {}", verifySignature);
            log.info("result:{}", result);
            return JSONUtil.toJsonStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 申请资金账单
     *
     * @param billDate 2020-06-14 当天账单后一天出，不然会出现「账单日期格式不正确」错误
     * @return 资金账单下载地址
     */
    @RequestMapping("/fundFlowBill")
    @ResponseBody
    public String fundFlowBill(@RequestParam(value = "billDate", required = false) String billDate) {
        try {
            if (StrUtil.isEmpty(billDate)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.DATE, -1);
                billDate = DateUtil.format(calendar.getTime(), "YYYY-MM-dd");
            }
            Map<String, String> params = new HashMap<>(12);
            params.put("bill_date", billDate);
            params.put("account_type", "BASIC");

            IJPayHttpResponse result = WxPayApi.v3(
                    RequestMethod.GET,
                    WxDomain.CHINA.toString(),
                    WxApiType.FUND_FLOW_BILL.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    params
            );
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(result, wxPayV3Bean.getPlatformCertPath());
            log.info("verifySignature: {}", verifySignature);
            log.info("result:{}", result);
            return JSONUtil.toJsonStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/billDownload")
    @ResponseBody
    public String billDownload(@RequestParam(value = "token") String token,
                               @RequestParam(value = "tarType", required = false) String tarType) {
        try {

            Map<String, String> params = new HashMap<>(12);
            params.put("token", token);
            if (StrUtil.isNotEmpty(tarType)) {
                params.put("tartype", tarType);
            }

            IJPayHttpResponse result = WxPayApi.v3(
                    RequestMethod.GET,
                    WxDomain.CHINA.toString(),
                    WxApiType.BILL_DOWNLOAD.toString(),
                    wxPayV3Bean.getMchId(),
                    getSerialNumber(),
                    null,
                    wxPayV3Bean.getKeyPath(),
                    params
            );
            log.info("result:{}", result);
            return JSONUtil.toJsonStr(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/payNotify", method = {org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
    @ResponseBody
    public void payNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>(12);
        try {
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String serialNo = request.getHeader("Wechatpay-Serial");
            String signature = request.getHeader("Wechatpay-Signature");

            log.info("timestamp:{} nonce:{} serialNo:{} signature:{}", timestamp, nonce, serialNo, signature);
            String result = HttpKit.readData(request);
            log.info("支付通知密文 {}", result);

            // 需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
            String plainText = WxPayKit.verifyNotify(serialNo, result, signature, nonce, timestamp,
                    wxPayV3Bean.getApiKey3(), wxPayV3Bean.getPlatformCertPath());

            log.info("支付通知明文 {}", plainText);

            if (StrUtil.isNotEmpty(plainText)) {
                response.setStatus(200);
                map.put("code", "SUCCESS");
                map.put("message", "SUCCESS");
            } else {
                response.setStatus(500);
                map.put("code", "ERROR");
                map.put("message", "签名错误");
            }
            response.setHeader("Content-type", ContentType.JSON.toString());
            response.getOutputStream().write(JSONUtil.toJsonStr(map).getBytes(StandardCharsets.UTF_8));
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}