import java.util.*;

class StackQueue {
    // https://www.interviewbit.com/problems/reverse-string/
    static String reverse(String A) {
        // push all characters of string to stack
        Stack<Character> s = new Stack<>();
        for (char c : A.toCharArray())
            s.push(c);

        // append characters from top of stack to result
        StringBuilder res = new StringBuilder();
        while (!s.empty())
            res.append(s.pop());

        return res.toString();
    }

    // https://www.interviewbit.com/problems/generate-all-parentheses/
    static int validMixParantheses(String A) {
        Stack<Character> s = new Stack<>();

        for (char c : A.toCharArray()) {
            // if open bracket, push to stack
            if (c == '(' || c == '[' || c == '{')
                s.push(c);
                // if close bracket and stack is empty, string is invalid
            else if (s.empty())
                return 0;
                // if matching closing bracket with stack top, pop from the stack
            else if ((c == ')' && s.peek() == '(') || (c == ']' && s.peek() == '[') || (c == '}' && s.peek() == '{'))
                s.pop();
                // not matching closing bracket with stack top, invalid string
            else
                return 0;
        }

        // if stack is empty, valid string else invalid string
        return s.empty() ? 1 : 0;
    }

    // https://www.interviewbit.com/problems/maxspprod/
    static int maxSpecialProduct(int[] A) {
        long p = 1000000007;
        long ans = -1;

        // get left and right specials with next greater element logic
        int[] leftSpecials = calculateLeftSpecials(A);
        int[] rightSpecials = calculateRightSpecials(A);
        // maximize the product first then take modulo
        for (int i = 0; i < A.length; i++)
            ans = Math.max(ans, (long) leftSpecials[i] * rightSpecials[i]);

        return (int) (ans % p);
    }

    // util to calculate right special index j such that j > i && A[j] > A[i]
    private static int[] calculateRightSpecials(int[] A) {
        int n = A.length;
        int[] nextGreater = new int[n];

        Stack<Integer> s = new Stack<>();
        s.push(0);

        for (int i = 1; i < n; i++) {
            // pop elements from stack and mark next greater as the current element
            while (!s.empty() && A[s.peek()] < A[i])
                nextGreater[s.pop()] = i;

            s.push(i);
        }

        return nextGreater;
    }

    // util to calculate left special index j such that j < i && A[j] > A[i]
    private static int[] calculateLeftSpecials(int[] A) {
        int n = A.length;
        int[] prevGreater = new int[n];

        Stack<Integer> s = new Stack<>();
        s.push(0);

        for (int i = 1; i < n; i++) {
            // pop elements from stack while stack is not empty and top of stack is smaller than arr[i]
            // we always have elements in decreasing order in a stack.
            while (!s.empty() && A[s.peek()] <= A[i])
                s.pop();
            // if stack becomes empty, then no element is greater on left side
            // else top of stack is previous greater
            if (!s.empty())
                prevGreater[i] = s.peek();

            s.push(i);
        }

        return prevGreater;
    }

    // https://www.interviewbit.com/problems/nearest-smaller-element/
    static int[] prevSmaller(int[] A) {
        int n = A.length;
        int[] G = new int[n];
        Arrays.fill(G, -1);

        Stack<Integer> s = new Stack<>();
        s.push(0);

        for (int i = 1; i < n; i++) {
            // pop elements from stack while stack is not empty and top of stack is greater than arr[i]
            // we always have elements in increasing order in a stack.
            while (!s.empty() && A[s.peek()] >= A[i])
                s.pop();
            // if stack becomes empty, then no element is smaller on left side
            // else top of stack is previous smaller
            if (!s.empty())
                G[i] = A[s.peek()];

            s.push(i);
        }

        return G;
    }

    // https://www.interviewbit.com/problems/largest-rectangle-in-histogram/
    static int largestRectangleArea(int[] A) {
        int n = A.length;
        int maxArea = 0;

        Stack<Integer> s = new Stack<>();
        s.push(0);

        for (int i = 1; i < n; i++) {
            // while bar on stack top is greater than current, pop top from the stack and compute area
            // with top as height, i as right position and index of element below current stack top as left position
            while (!s.empty() && A[s.peek()] > A[i]) {
                int top = s.pop();
                int areaWithTop = A[top] * (s.empty() ? i : i - s.peek() - 1);
                maxArea = Math.max(maxArea, areaWithTop);
            }

            // stack is empty or current bar is higher
            s.push(i);
        }

        // check for the remaining sorted bars in stack
        while (!s.empty()) {
            int top = s.pop();
            int areaWithTop = A[top] * (s.empty() ? n : n - s.peek() - 1);
            maxArea = Math.max(maxArea, areaWithTop);
        }

        return maxArea;
    }

    // https://www.interviewbit.com/problems/first-non-repeating-character-in-a-stream-of-characters/
    static String firstNonRepeating(String A) {
        // queue to store first non-repeating char
        Queue<Character> q = new LinkedList<>();
        // map to keep frequency count of each char
        int[] map = new int[26];
        StringBuilder res = new StringBuilder();

        for (char c : A.toCharArray()) {
            map[c - 'a']++;
            // if current char is appearing for the first time
            if (map[c - 'a'] == 1)
                q.add(c);

            // keep removing repeating chars from the front of queue until a non-repeating char is found
            while (!q.isEmpty() && map[q.peek() - 'a'] != 1)
                q.remove();

            // if no non-repeating char, append '#'
            if (q.isEmpty())
                res.append("#");
            else
                res.append(q.peek());
        }

        return res.toString();
    }

    // https://www.interviewbit.com/problems/sliding-window-maximum/
    @SuppressWarnings("ConstantConditions")
    static int[] slidingWindowMax(final int[] A, int B) {
        int n = A.length;
        // if window is larger than array, take max of the array
        if (B > n)
            B = n;

        int[] res = new int[n - B + 1];
        Deque<Integer> q = new LinkedList<>();
        // for the first window
        for (int i = 0; i < B; i++) {
            // remove all elements smaller than current from the back end of deque
            while (!q.isEmpty() && A[i] >= A[q.peekLast()])
                q.pollLast();
            // add current element to the back end
            q.addLast(i);
        }

        // max of first window
        res[0] = A[q.peekFirst()];
        // for the remaining windows
        for (int i = B; i < n; i++) {
            // if first element in deque is out of range, remove
            if (!q.isEmpty() && q.peekFirst() <= i - B)
                q.pollFirst();

            // remove all elements smaller than current from the back end of deque
            while (!q.isEmpty() && A[i] >= A[q.peekLast()])
                q.pollLast();

            // add current element to the back end
            q.addLast(i);
            // max of the current window
            res[i - B + 1] = A[q.peekFirst()];
        }

        return res;
    }

    // https://www.interviewbit.com/problems/simplify-directory-path/
    static String simplifyDirPath(String A) {
        // use deque as stack to not reverse the result at the end
        Deque<String> q = new LinkedList<>();

        // split by "/"
        for (String s : A.split("/")) {
            // do nothing if empty string or "."
            if (s.isEmpty() || s.equals("."))
                continue;

            // move up in the path (pop current folder)
            if (s.equals("..")) {
                if (!q.isEmpty())
                    q.removeLast();
            }
            // add current folder to path
            else
                q.addLast(s);
        }

        // if empty path
        if (q.isEmpty())
            return "/";

        // else construct path by traversing stack from the bottom (deque)
        StringBuilder res = new StringBuilder();
        while (!q.isEmpty()) {
            res.append("/");
            res.append(q.pollFirst());
        }

        return res.toString();
    }

    // https://www.interviewbit.com/problems/redundant-braces/
    static int redundantBraces(String A) {
        Stack<Character> s = new Stack<>();

        for (char c : A.toCharArray()) {
            // if closing braces encountered
            if (c == ')') {
                char top = s.pop();
                int cnt = 0;

                // keep popping from stack till corresponding open parantheses is encountered
                while (top != '(') {
                    top = s.pop();
                    cnt++;
                }

                // if there was no or only one operator/operand between
                if (cnt < 2)
                    return 1;
            }
            // push operators, operands and open parantheses on the stack
            else
                s.push(c);
        }

        // no redundant parentheses as all pairs contained at least one operator
        return 0;
    }

    // https://www.interviewbit.com/problems/min-stack/
    static class MinStack {
        // s: stack for entries, minStack: stack for minimum values
        private final Stack<Integer> s, minStack;

        public MinStack() {
            s = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int x) {
            // push x to the main stack
            s.push(x);

            // if minStack is empty or x is a new minimum push to minStack
            if (minStack.empty() || x <= minStack.peek())
                minStack.push(x);
        }

        public void pop() {
            // if main stack is not empty
            if (!s.empty()) {
                int top = s.pop();

                // if minStack is not empty and top of the main stack is a minimum
                if (!minStack.empty() && top == minStack.peek())
                    minStack.pop();
            }
        }

        public int top() {
            // if main stack is empty
            if (s.empty())
                return -1;
            return s.peek();
        }

        public int getMin() {
            // if minStack is empty
            if (minStack.empty())
                return -1;
            return minStack.peek();
        }
    }

    // https://www.interviewbit.com/problems/evaluate-expression/
    static int evaluate(String[] A) {
        // if no operands and operators
        if (A.length == 0)
            return 0;

        Stack<Integer> s = new Stack<>();
        for (String str : A) {
            // if operator, apply operator to top two elements of stack and push new result after popping them
            if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
                int b = s.pop(), a = s.pop(), op;

                switch (str) {
                    case "+":
                        op = a + b;
                        break;
                    case "-":
                        op = a - b;
                        break;
                    case "*":
                        op = a * b;
                        break;
                    default:
                        op = a / b;
                        break;
                }

                s.push(op);
            }
            // else if operand, push the operand to the stack
            else
                s.push(Integer.parseInt(str));
        }

        // last number left will be the result of the expression
        return s.pop();
    }

    // https://www.interviewbit.com/problems/rain-water-trapped/
    static int rainWaterTrapped(final int[] A) {
        int n = A.length;
        int res = 0, l = 0, r = n - 1, leftMax = 0, rightMax = 0;

        // keep two pointers at extreme ends
        while (l <= r) {
            // if left height is smaller, water will be trapped at this point
            if (A[l] < A[r]) {
                leftMax = Math.max(leftMax, A[l]);
                // water trapped at this point is max height on the left - height of this level
                res += leftMax - A[l];
                l++;
            }
            // else right height is smaller, water will be trapped at this point
            else {
                rightMax = Math.max(rightMax, A[r]);
                // water trapped at this point is max height on the right - height of this level
                res += rightMax - A[r];
                r--;
            }
        }

        return res;
    }

    // https://www.interviewbit.com/problems/balanced-parantheses/
    static int balancedParentheses(String A) {
        // maintain count of open parentheses
        int open = 0;

        for (char c : A.toCharArray()) {
            // increment count of open parentheses
            if (c == '(')
                open++;
                // if at least one open parentheses is present, balance that with current closing one
            else if (open > 0)
                open--;
                // no open parentheses to balance this closed one, invalid string
            else
                return 0;
        }

        // if all open balanced, valid string else invalid string
        return open == 0 ? 1 : 0;
    }

    // https://www.interviewbit.com/problems/subtract/
    static LinkedLists.ListNode subtract(LinkedLists.ListNode head) {
        LinkedLists.ListNode slow = head, fast = head.next;
        // find middle of the linked list
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Stack<LinkedLists.ListNode> s = new Stack<>();
        fast = slow.next;

        // push the nodes starting from middle on the stack
        while (fast != null) {
            s.push(fast);
            fast = fast.next;
        }

        slow = head;
        // start linked list traversal from head.
        // Keep popping nodes from stack and update the value of nodes from beginning
        while (!s.empty()) {
            slow.val = s.pop().val - slow.val;
            slow = slow.next;
        }

        return head;
    }

    // https://www.interviewbit.com/problems/nextgreater/
    static int[] nextGreater(int[] A) {
        int n = A.length;
        int[] G = new int[n];
        Arrays.fill(G, -1);

        Stack<Integer> s = new Stack<>();
        s.push(0);

        for (int i = 1; i < n; i++) {
            // pop elements from stack and mark next greater as the current element
            while (!s.empty() && A[s.peek()] < A[i])
                G[s.pop()] = A[i];

            s.push(i);
        }

        return G;
    }
}