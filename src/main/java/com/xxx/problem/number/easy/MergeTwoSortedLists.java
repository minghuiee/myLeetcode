package com.xxx.problem.number.easy;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j
public class MergeTwoSortedLists {
    /**
     * Merge two sorted linked lists and return it as a new sorted list. The new list should be made by splicing together the nodes of the first two lists.
     * <p>
     * Example:
     * Input: 1->2->4, 1->3->4
     * Output: 1->1->2->3->4->4
     * <p>
     * Example:
     * Input: 5 -> 6 -> 7,1 -> 2 -> 4 -> 9
     * Output: 1 -> 2 -> 4 -> 5
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode headNode = l1;
        if (l1.val > l2.val) {
            headNode = l2;
            l2 = l1;
            l1 = headNode;
        }
        while (true) {
            if (l1.next == null) {
                l1.next = l2;
                break;
            } else {
                if (l1.next.val <= l2.val) {
                    l1 = l1.next;
                } else {
                    ListNode node = l1.next;
                    l1.next = l2;
                    l2 = l2.next;
                    l1.next.next = node;
                    l1 = l1.next;
                    if (l2 == null) {
                        break;
                    }
                }
            }
        }
        return headNode;
    }

    /**
     * Input: 1->2->4, 1->3->4
     *
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.val < l2.val) {
            l1.next = mergeTwoLists2(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists2(l1, l2.next);
            return l2;
        }
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        MergeTwoSortedLists lists = new MergeTwoSortedLists();
        //example 1
//        ListNode node3 = lists.new ListNode(4);
//        ListNode node2 = lists.new ListNode(2, node3);
//        ListNode node1 = lists.new ListNode(1, node2);
//        ListNode node7 = lists.new ListNode(5);
//        ListNode node6 = lists.new ListNode(4, node7);
//        ListNode node5 = lists.new ListNode(3,node6);
//        ListNode node4 = lists.new ListNode(1, node5);
        //example 2
        ListNode node3 = lists.new ListNode(7);
        ListNode node2 = lists.new ListNode(6, node3);
        ListNode node1 = lists.new ListNode(5, node2);
        ListNode node7 = lists.new ListNode(9);
        ListNode node6 = lists.new ListNode(4, node7);
        ListNode node5 = lists.new ListNode(2, node6);
        ListNode node4 = lists.new ListNode(1, node5);

        //example 3
//        ListNode node1 = lists.new ListNode(2);
//        ListNode node4 = lists.new ListNode(1);
//        ListNode mergeNode1 = lists.mergeTwoLists(node1, node4);
        ListNode mergeNode2 = lists.mergeTwoLists2(node1, node4);
    }
}
