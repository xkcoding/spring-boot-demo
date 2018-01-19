package com.xkcoding.util;

import com.google.common.collect.Lists;
import com.xiaoleilu.hutool.json.JSONUtil;
import com.xkcoding.util.domain.TreeNode;

import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 把一个list集合,里面的bean含有 parentId 转为树形式
 * </p>
 *
 * @package: com.xkcoding.util
 * @description： 把一个list集合, 里面的bean含有 parentId 转为树形式
 * @author: yangkai.shen
 * @date: Created in 2017/12/7 上午10:26
 * @copyright: Copyright (c) 2017
 * @version: 0.0.1
 * @modified: yangkai.shen
 */
public class TreeUtil {

	/**
	 * 测试层级
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		List<TreeNode> treeObjectList = Lists.newArrayList();

		treeObjectList.add(new TreeNode(1, "总部", 0, "0", 1, "总部", Lists.<TreeNode>newArrayList()));
		treeObjectList.add(new TreeNode(2, "技术部", 1, "0,1", 1, "技术部", Lists.<TreeNode>newArrayList()));
		treeObjectList.add(new TreeNode(3, "软件部", 2, "0,1,2", 1, "软件部", Lists.<TreeNode>newArrayList()));
		treeObjectList.add(new TreeNode(4, "硬件部", 2, "0,1,2", 2, "硬件部", Lists.<TreeNode>newArrayList()));
		treeObjectList.add(new TreeNode(5, "维修部", 4, "0,1,2,4", 1, "维修部", Lists.<TreeNode>newArrayList()));
		treeObjectList.add(new TreeNode(6, "采购部", 4, "0,1,2,4", 2, "采购部", Lists.<TreeNode>newArrayList()));

		TreeUtil mt = new TreeUtil();

		List<TreeNode> ns = mt.getChildTreeNodes(treeObjectList, 0);

		for (TreeNode m : ns) {
			System.out.println(m);
			System.out.println(JSONUtil.parseObj(m, false).toStringPretty());
		}

		long end = System.currentTimeMillis();

		System.out.println("用时:" + (end - start) + "ms");
	}

	/**
	 * 根据父节点 id 获取所有子节点
	 *
	 * @param list     列表
	 * @param parentId 父节点 id
	 * @return 所有子节点
	 */
	public List<TreeNode> getChildTreeNodes(List<TreeNode> list, Integer parentId) {
		List<TreeNode> returnList = Lists.newArrayList();
		for (Iterator<TreeNode> iterator = list.iterator(); iterator.hasNext(); ) {
			TreeNode t = iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (t.getParentId().equals(parentId)) {
				recursionFn(list, t);
				returnList.add(t);
			}
		}
		return returnList;
	}

	/**
	 * 递归列表
	 *
	 * @param list 列表
	 * @param t    树节点
	 */
	private void recursionFn(List<TreeNode> list, TreeNode t) {
		// 得到子节点列表
		List<TreeNode> childList = getChildList(list, t);

		t.setChildren(childList);
		for (TreeNode tChild : childList) {
			// 判断是否有子节点
			if (hasChild(list, tChild)) {
				Iterator<TreeNode> it = childList.iterator();
				while (it.hasNext()) {
					TreeNode n = it.next();
					recursionFn(list, n);
				}
			}
		}
	}

	/**
	 * 判断是否存在子节点
	 *
	 * @param list
	 * @param tChild
	 * @return
	 */
	private boolean hasChild(List<TreeNode> list, TreeNode tChild) {
		return getChildList(list, tChild).size() > 0;
	}

	/**
	 * 获得子节点列表
	 *
	 * @param list
	 * @param t
	 * @return
	 */
	private List<TreeNode> getChildList(List<TreeNode> list, TreeNode t) {
		List<TreeNode> tlist = Lists.newArrayList();
		Iterator<TreeNode> it = list.iterator();
		while (it.hasNext()) {
			TreeNode n = it.next();
			if (n.getParentId().equals(t.getId())) {
				tlist.add(n);
			}
		}
		return tlist;
	}

}
