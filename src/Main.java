import java.util.Stack;

public class Main {
    public static void main(String[] args) {
       // System.out.println("Hello, World!");
        String bal = "{[()]}";
        String bal1 = "{[()()]{}{[()()]}}";
        String bal2 = "((()))[]{}{}";
        String bal3 = "[{()}]{}()";
        String bal4 = "({[({})]})";

        String bal5 = "{[}()";
        String bal6 = "(()[{}]";
        String bal7 = "[({[)]})";
        String bal8 = "({[})]";
        String bal9 = "((())";


        String infix = "x^y/(5*z)+2";
        String infix1 = "a+b*(c^d-e)^(f+g*h)-i";
        String infix2 = "(x+2)*5";
        String infix3 = "(3+4)*(5+6)";
        String infix4 = "a+b*c-d";
        String infix5 = "x+(y-z)*p";
        String infix6 = "(x*y+z)/(w+q)-(a+b)*(c+d)";
        String infix7 = "3[z]2[2[y]pq4[2[jk]e1[f]]]ef";
        String infix8 = "3[z]2[2[y]pq4[2[jk]e1[f]]]ef";

        String test1 = "3[abc]";  // "abcabcabc"
        String test2 = "2[ab3[c]]";  // "abcccabccc"
        String test3 = "10[a]";  // "aaaaaaaaaa"
        String test4 = "3[a2[c]]";  // "accaccacc"
        String test5 = "2[abc3[de]]";  // "abcdeabcde"
        String test6 = "1[a2[b3[c]]]";  // "abccbccbcc"
        String test7 = "4[ab2[cd]]";  // "abcdcdabcdcdabcdcd"
        String test8 = "5[xyz2[abc]]";  // "xyzabcabcxyzabcabcxyzabcabcxyzabcabcxyzabcabc"
        String test9 = "2[3[a]2[b]]";  // "aaabbaaabbb"
        String test10 = "3[xyz]2[abc]";  // "xyzxyzxyzabcabc"


        String[] infixes = {infix, infix1, infix2, infix3, infix4, infix5, infix6, infix7, infix8};

        String[] bals = {bal, bal1, bal2, bal3, bal4, bal5, bal6, bal7, bal8, bal9};

        String[] decodes = {test1, test2,test3,test4,test5,test6,test7,test8,test9,test10};

       for(int i = 0; i < bals.length; i ++){
           System.out.println("Is this String balanced? " + bals[i] );
           System.out.println(isBalanced(bals[i]));
       }

       System.out.println('\n');
        for(int i = 0; i < infixes.length; i ++){
            System.out.println("Input Infix: " + infixes[i]);
            System.out.println(infixToPostFix(infixes[i]));
        }
        System.out.println('\n');
        for(int i = 0; i < decodes.length; i ++){
            System.out.println("String to decode: " + decodes[i]);
            System.out.println(decodeString(decodes[i]));
        }











        //System.out.println("abcd^e-fgh*+^*+i-");


    }


    public static String isBalanced(String s) {
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '{' || s.charAt(i) == '(' || s.charAt(i) == '['){
                st.push(s.charAt(i));
            }
            else if (st.empty()){
                return "NO";
            }else if ( s.charAt(i) == '}' && st.peek() != '{' ||
                        s.charAt(i) == ']' && st.peek() != '[' ||
                        s.charAt(i) == ')' && st.peek() != '(' ){
               return "NO";
            }
            else {
                st.pop();
            }
        }

        if(st.empty()){
            return "YES";
        }
        return "NO";
    }

    public static String decodeString(String s) {

        Stack<Integer> num = new Stack<>();
        Stack<String> str = new Stack<>();
        int integer = 0;
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                integer = integer * 10 + (s.charAt(i) - '0');
            } else if (s.charAt(i) == '[') {
                num.push(integer);
                str.push(res.toString());
                res = new StringBuilder();
                integer = 0;
            } else if (s.charAt(i) == ']') {
                StringBuilder buf = new StringBuilder(str.pop());
                int count = 0;
                int j = num.pop();
                while (count < j) {
                    buf.append(res);
                    count++;
                }
                res = buf;
            } else {
                res.append(s.charAt(i));
            }
        }
        return res.toString();
    }

    public static String infixToPostFix(String infix){

        Stack<Character> op = new Stack<>();
        String postFix = "";
        char c;
        int prec;
        for(int i = 0; i < infix.length(); i++){
            c = infix.charAt(i);
            prec = gibVal(c);
          if (c >= 'a' && c <= 'z' || (c >= '0' && c <= '9')) {
              postFix += c;
          } else if (c == ')') {
                while(op.peek() != '('){
                    postFix += op.pop();
                }
                op.pop();
          } else if (c == '(') {
              op.push(c);
          } else if (op.empty()) {
              op.push(c);
          } else if ( prec > gibVal(op.peek()) ) {
               op.push(c);
          } else if ( prec <= gibVal(op.peek()) ) {
                  while (!op.empty() && gibVal(op.peek()) >= gibVal(c)) {
                      if(op.peek() == '('){
                          break;
                      }
                      postFix += op.pop();
                  }
                  op.push(c);
          }
        }
       while(!op.empty()){
           postFix += op.pop();
       }
        return postFix;
    }

    public static int gibVal(char op){

        if(op == '*' || op == '/' ) {
            return 1;
        }
        else if(op == '^'){
            return 2;
        }
        else if(op == '+' || op == '-'){
            return 0;
        }
        return -1;
    }

}