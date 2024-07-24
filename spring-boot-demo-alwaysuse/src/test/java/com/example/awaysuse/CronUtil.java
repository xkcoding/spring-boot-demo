package com.example.awaysuse;

import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CronUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CronUtil.class);

    private static final String QUESTION = "?";

    private static final String ASTERISK = "*";

    private static final String COMMA = ",";

    /**
     * 替换 分钟、小时、日期、星期
     */
    private static final String ORIGINAL_CRON = "0 %s %s %s * %s";

    /**
     * 检查cron表达式的合法性
     *
     * @param cron cron exp
     * @return true if valid
     */
    public boolean checkValid(String cron) {
        try {
            // SPRING应该是使用最广泛的类型,但假若任务调度依赖于xxl-job平台,则需要调整为CronType.QUARTZ
            CronDefinition cronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.SPRING);
            CronParser parser = new CronParser(cronDefinition);
            parser.parse(cron);
        } catch (IllegalArgumentException e) {
            LOGGER.error(String.format("cron=%s not valid", cron));
            return false;
        }
        return true;
    }

    public String buildCron(List<Integer> minutes, List<Integer> hours, List<Integer> weekdays) {
        String minute;
        if (minutes.equals(this.getInitMinutes())) {
            minute = ASTERISK;
        } else {
            minute = StringUtils.join(minutes, COMMA);
        }
        String hour;
        if (hours.equals(this.getInitHours())) {
            hour = ASTERISK;
        } else {
            hour = StringUtils.join(hours, COMMA);
        }
        String weekday;
        if (weekdays.equals(this.getInitWeekdays())) {
            weekday = QUESTION;
        } else {
            weekday = StringUtils.join(weekdays, COMMA);
        }
        // 重点：星期和日字段冲突，判断周日的前端输入
        if (weekday.equals(QUESTION)) {
            return String.format(ORIGINAL_CRON, minute, hour, ASTERISK, weekday);
        } else {
            return String.format(ORIGINAL_CRON, minute, hour, QUESTION, weekday);
        }
    }

    /**
     * 解析db cron expression展示到前端
     *
     * @param cron cron
     * @return minutes/hours/weekdays
     */
    public CustomCronField parseCon(String cron) {
        if (!this.checkValid(cron)) {
            return null;
        }
        List<String> result = Arrays.asList(cron.trim().split(" "));
        CustomCronField field = new CustomCronField();
        if (result.get(1).contains(COMMA)) {
            field.setMinutes(Arrays.stream(result.get(1).split(COMMA)).map(Integer::parseInt).collect(Collectors.toList()));
        } else if (result.get(1).equals(ASTERISK)) {
            field.setMinutes(this.getInitMinutes());
        } else {
            field.setMinutes(Lists.newArrayList(Integer.parseInt(result.get(1))));
        }
        if (result.get(2).contains(COMMA)) {
            field.setHours(Arrays.stream(result.get(2).split(COMMA)).map(Integer::parseInt).collect(Collectors.toList()));
        } else if (result.get(2).equals(ASTERISK)) {
            field.setHours(this.getInitHours());
        } else {
            field.setHours(Lists.newArrayList(Integer.parseInt(result.get(2))));
        }
        if (result.get(5).contains(COMMA)) {
            field.setWeekdays(Arrays.stream(result.get(5).split(COMMA)).map(Integer::parseInt).collect(Collectors.toList()));
        } else if (result.get(5).equals(QUESTION)) {
            field.setWeekdays(this.getInitWeekdays());
        } else {
            field.setWeekdays(Lists.newArrayList(Integer.parseInt(result.get(5))));
        }
        return field;
    }

    private List<Integer> initArray(Integer num) {
        List<Integer> result = Lists.newArrayListWithCapacity(num);
        for (int i = 0; i <= num; i++) {
            result.add(i);
        }
        return result;
    }

    private List<Integer> getInitMinutes() {
        return this.initArray(59);
    }

    private List<Integer> getInitHours() {
        return this.initArray(23);
    }

    private List<Integer> getInitWeekdays() {
        return this.initArray(7).subList(1, 8);
    }

    @Data
    public static class CustomCronField {
        private List<Integer> minutes;
        private List<Integer> hours;
        private List<Integer> weekdays;
    }
}
