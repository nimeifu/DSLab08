package com.company;

import java.util.ArrayList;
import java.util.Stack;

/**
 * This is lab 8
 @author Genjie Liu
        * @version 1.0
        * Lab section:Fri 10:25-11:20 Tuan Tran
*/
public class Expression {

    private String infix;

    public Expression (String infix)
    {
        this.infix=infix;
    }

    public int OperatorOrder (String a)
    {
        if(a=="+" || a=="-")
        {
            return 1;
        }
        else if (a=="*" ||a=="/")
        {
            return 2;
        }
        return 0;
    }

    public ArrayList<String> infixToPostfix()
    {
        Stack<String> stack= new Stack<>();
        ArrayList<String> postFixString= new ArrayList<>();
        String[]splited=infix.split("(?<=[-+*/])|(?=[-+*/])|(?<=[()])|(?=[()])");
        /*
        for (String s:splited
             ) {
            System.out.println(s);
        }
        */
        for(int i=0;i<splited.length;i++) {
            String c=splited[i];

            if (c.matches("[0-9]+")) {
                postFixString.add(c);
            }
            else
                if (c.equals("(")) {
                stack.push(c);
            }
            else if (c.equals(")")) {

                while (!stack.isEmpty()&&!stack.peek().equals("(")) {
                    postFixString.add(stack.pop());
                }
                stack.pop();
            }
            else {
                while (!stack.isEmpty() && !stack.peek().equals(")") && OperatorOrder(c) <= OperatorOrder(stack.peek()))
                {
                    postFixString.add(stack.pop());
                }
                stack.push(c);
            }
        }


        while (!stack.isEmpty())
        {
            postFixString.add(stack.pop());
        }

        return postFixString;
    }



    public int evaluate() {
        Stack<Integer> stack = new Stack<>();
        Expression expression = new Expression(infix);
        ArrayList<String> postfix = expression.infixToPostfix();


        for (int i=0;i<postfix.size();i++){
            String c=postfix.get(i);

             if(c.matches("[0-9]+"))
            {


                //push the number in stack
                stack.push(Integer.parseInt(c));
            }
            else if (c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/")){
                int val1=stack.pop();
                int val2=stack.pop();
                switch (c) {
                    case "+":
                        stack.push(val2 + val1);
                        break;

                    case "-":
                        stack.push(val2 - val1);
                        break;

                    case "/":
                        stack.push(val2 / val1);
                        break;

                    case "*":
                        stack.push(val2 * val1);
                        break;
                }
            }
        }
        return stack.pop();
    }


    public static void main(String[] args) {
        Expression expression=new Expression("(13+4)*(5+10)");
        System.out.println(expression.infixToPostfix());
        System.out.println(expression.evaluate());

    }
}
