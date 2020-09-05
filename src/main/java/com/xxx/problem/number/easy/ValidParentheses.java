package com.xxx.problem.number.easy;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

@Slf4j
public class ValidParentheses {

    /**
     * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
     * An input string is valid if:
     * Open brackets must be closed by the same type of brackets.
     * Open brackets must be closed in the correct order.
     * Example 1:
     * Input: s = "()"
     * Output: true
     * <p>
     * Example 2:
     * Input: s = "()[]{}"
     * Output: true
     * <p>
     * Example 3:
     * Input: s = "(]"
     * Output: false
     * <p>
     * Example 4:
     * Input: s = "([)]"
     * Output: false
     * <p>
     * Example 5:
     * Input: s = "{[]}"
     * Output: true
     */
    // Hash table that takes care of the mappings.
    private HashMap<Character, Character> mappings;

    // Initialize hash map with mappings. This simply makes the code easier to read.
    public ValidParentheses() {
        this.mappings = new HashMap<Character, Character>();
        this.mappings.put(')', '(');
        this.mappings.put('}', '{');
        this.mappings.put(']', '[');
    }

    public boolean isValid(String s) {
        // Initialize a stack to be used in the algorithm.
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // If the current character is a closing bracket.
            if (this.mappings.containsKey(c)) {
                // Get the top element of the stack. If the stack is empty, set a dummy value of '#'
                char topElement = stack.empty() ? '#' : stack.pop();
                // If the mapping for this bracket doesn't match the stack's top element, return false.
                if (topElement != this.mappings.get(c)) {
                    return false;
                }
            } else {
                // If it was an opening bracket, push to the stack.
                stack.push(c);
            }
        }
        // If the stack still contains elements, then it is an invalid expression.
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        boolean b = new ValidParentheses().isValidByMyResult("{[[]{}]}()()");
        log.info("{}", b);
    }

    /**
     * 我的解法
     * 
     */
    public boolean isValidByMyResult(String s) {
        int quantity = 0;
        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            if (c.equals('(') || c.equals('[') || c.equals('{')) {
                quantity++;
                list.addFirst(c);
            } else if (c.equals(')') || c.equals(']') || c.equals('}')) {
                quantity--;
                if (quantity < 0) {
                    return false;
                }
                Character firstC = list.getFirst();
                if (firstC.equals('(') && c.equals(')') || firstC.equals('[') && c.equals(']') || firstC.equals('{') && c.equals('}')) {
                    list.removeFirst();
                    continue;
                }
                return false;
            }
        }
        return quantity == 0;
    }
}
