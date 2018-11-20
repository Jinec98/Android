package com.example.jinec.myapplication;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static double ans = 0;
    static double tempAns = 0;
    static boolean press = false;
    static String preNum;
    static String nowNum;
    static ArrayList<Double> Num = new ArrayList<>();
    static ArrayList<String> Oper = new ArrayList<>();
    static ArrayList<Double> TempNum = new ArrayList<>();
    static ArrayList<String> TempOper = new ArrayList<>();
    static DecimalFormat decimalFormat;
    static int length = 0;

    public void formatPrint()
    {
        int intNum = 0;
        int decNum = 0;
        double absAns = Math.abs(ans);
        int ansInt = (int)absAns;

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            length = 15;
            if(absAns != 0 && (absAns >= 1e15 || absAns < 1e-14))
            {
                decimalFormat = new DecimalFormat("0.##E0");
                return;
            }
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            length = 9;
            if(absAns != 0 && (absAns >= 1e9 || absAns < 1e-8))
            {
                decimalFormat = new DecimalFormat("0.##E0");
                return;
            }
        }

        if(absAns < 1)
            intNum = 1;
        else
        {
            while (ansInt > 0) {
                ansInt /= 10;
                intNum++;
            }
        }
        if(absAns == (int)absAns)
            decNum = 0;
        else
        {
            while(true)
            {
                decNum++;
                if(absAns * Math.pow(10, decNum) % 1 == 0)
                    break;
            }
        }
        if(intNum + decNum > length)
        {
            decNum = length - intNum;
            if(decNum < 0)
                decNum = 0;
        }
        if(decNum == 0)
        {
            decimalFormat = new DecimalFormat("#");
        }
        else
        {
            String formatStr = "";
            if(intNum == 0)
                formatStr += "#";
            for(int i = 0; i < intNum; i++)
                formatStr += "#";
            formatStr += ".";
            for(int i = 0; i < decNum; i++)
                formatStr += "#";
            decimalFormat = new DecimalFormat(formatStr);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);

        final TextView answerText = (TextView)findViewById(R.id.AnswerText);
        Button clearButton  = (Button)findViewById(R.id.ClearButton);
        Button negationButton = (Button)findViewById(R.id.NegationButton);
        Button percentButton = (Button)findViewById(R.id.PercentButton);
        Button divideButton = (Button)findViewById(R.id.DivideButton);
        Button multiplyButton = (Button)findViewById(R.id.MultiplyButton);
        Button minusButton = (Button)findViewById(R.id.MinusButton);
        Button addButton = (Button)findViewById(R.id.AddButton);
        final Button answerButton = (Button)findViewById(R.id.AnswerButton);
        Button dotButton = (Button)findViewById(R.id.DotButton);
        Button button0 = (Button)findViewById(R.id.Button0);
        Button button1 = (Button)findViewById(R.id.Button1);
        Button button2 = (Button)findViewById(R.id.Button2);
        Button button3 = (Button)findViewById(R.id.Button3);
        Button button4 = (Button)findViewById(R.id.Button4);
        Button button5 = (Button)findViewById(R.id.Button5);
        Button button6 = (Button)findViewById(R.id.Button6);
        Button button7 = (Button)findViewById(R.id.Button7);
        Button button8 = (Button)findViewById(R.id.Button8);
        Button button9 = (Button)findViewById(R.id.Button9);



        formatPrint();
        if(decimalFormat != null)
            answerText.setText(decimalFormat.format(ans));
        else
            answerText.setText("" + ans);

        Button.OnClickListener buttonListener = new Button.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch (v.getId())
                {
                    case R.id.ClearButton:
                        Num.clear();
                        Oper.clear();
                        TempNum.clear();
                        TempOper.clear();
                        answerText.setText("0");
                        ans = 0;
                        tempAns = 0;
                        return;
                    case R.id.NegationButton:
                        ans = ans * -1;
                        formatPrint();
                        if(decimalFormat != null)
                            answerText.setText(decimalFormat.format(ans));
                        else
                            answerText.setText("" + ans);
                        return;
                    case R.id.PercentButton:
                        ans = ans * 0.01;
                        formatPrint();
                        if(decimalFormat != null)
                            answerText.setText(decimalFormat.format(ans));
                        else
                            answerText.setText("" + ans);
                        return;
                    case R.id.DivideButton:
                        TempNum.clear();
                        TempOper.clear();
                        TempNum.addAll(Num);
                        TempOper.addAll(Oper);
                        tempAns = ans;

                        press = true;

                        if(TempOper.size() == 0)
                        {
                            TempNum.add(tempAns);
                            tempAns = 0;
                            TempOper.add("/");
                            return;
                        }

                        if(TempOper.get(TempOper.size()-1).equals("/") || TempOper.get(TempOper.size()-1).equals("*"))
                        {
                            int operIdx = TempOper.size()-1;
                            int numIdx = TempNum.size()-1;
                            double preAns = tempAns;
                            while(TempOper.get(operIdx).equals("/") || TempOper.get(operIdx).equals("*"))
                            {
                                if(TempOper.get(operIdx).equals("/"))
                                {
                                    preAns = TempNum.get(numIdx) / preAns;
                                }
                                else if(TempOper.get(operIdx).equals("*"))
                                {
                                    preAns = TempNum.get(numIdx) * preAns;
                                }
                                TempNum.remove(numIdx);
                                TempOper.remove(operIdx);
                                operIdx = TempOper.size()-1;
                                numIdx = TempNum.size()-1;
                                if(numIdx < 0 || operIdx < 0)
                                    break;
                            }
                            tempAns = preAns;
                            Num.add(tempAns);
                            formatPrint();
                            if(decimalFormat != null)
                                answerText.setText(decimalFormat.format(tempAns));
                            else
                                answerText.setText("" + tempAns);
                        }
                        else
                        {
                            TempNum.add(tempAns);
                            formatPrint();
                            if(decimalFormat != null)
                                answerText.setText(decimalFormat.format(tempAns));
                            else
                                answerText.setText("" + tempAns);
                        }
                        tempAns = 0;
                        TempOper.add("/");


                        return;
                    case R.id.MultiplyButton:
                        TempNum.clear();
                        TempOper.clear();
                        TempNum.addAll(Num);
                        TempOper.addAll(Oper);
                        tempAns = ans;

                        press = true;

                        if(TempOper.size() == 0)
                        {
                            TempNum.add(tempAns);
                            tempAns = 0;
                            TempOper.add("*");
                            return;
                        }

                        if(TempOper.get(TempOper.size()-1).equals("/") || TempOper.get(TempOper.size()-1).equals("*"))
                        {
                            int operIdx = TempOper.size()-1;
                            int numIdx = TempNum.size()-1;
                            double preAns = tempAns;
                            while(TempOper.get(operIdx).equals("/") || TempOper.get(operIdx).equals("*"))
                            {
                                if(TempOper.get(operIdx).equals("/"))
                                {
                                    preAns = TempNum.get(numIdx) / preAns;
                                }
                                else if(TempOper.get(operIdx).equals("*"))
                                {
                                    preAns = TempNum.get(numIdx) * preAns;
                                }
                                TempNum.remove(numIdx);
                                TempOper.remove(operIdx);
                                operIdx = TempOper.size()-1;
                                numIdx = TempNum.size()-1;
                                if(numIdx < 0 || operIdx < 0)
                                    break;
                            }
                            tempAns = preAns;
                            TempNum.add(tempAns);
                            formatPrint();
                            if(decimalFormat != null)
                                answerText.setText(decimalFormat.format(tempAns));
                            else
                                answerText.setText("" + tempAns);
                        }
                        else
                        {
                            TempNum.add(tempAns);
                            formatPrint();
                            if(decimalFormat != null)
                                answerText.setText(decimalFormat.format(tempAns));
                            else
                                answerText.setText("" + tempAns);
                        }
                        tempAns = 0;

                        TempOper.add("*");
                        return;
                    case R.id.MinusButton:
                        TempNum.clear();
                        TempOper.clear();
                        TempNum.addAll(Num);
                        TempOper.addAll(Oper);
                        tempAns = ans;

                        press = true;

                        if(TempOper.size() == 0)
                        {
                            TempNum.add(tempAns);
                            tempAns = 0;
                            TempOper.add("-");
                            return;
                        }

                        while(TempOper.size() != 0 || TempNum.size() != 0)
                        {
                            if (TempOper.get(TempOper.size() - 1).equals("/") || TempOper.get(TempOper.size() - 1).equals("*")) {
                                double preAns = tempAns;
                                if (TempOper.get(TempOper.size() - 1).equals("/")) {
                                    preAns = TempNum.get(TempNum.size() - 1) / preAns;
                                } else if (TempOper.get(TempOper.size() - 1).equals("*")) {
                                    preAns = TempNum.get(TempNum.size() - 1) * preAns;
                                }
                                TempNum.remove(TempNum.size() - 1);
                                TempOper.remove(TempOper.size() - 1);
                                tempAns = preAns;
                            } else {
                                int operIdx = TempOper.size() - 1;
                                int numIdx = TempNum.size() - 1;
                                double preAns = tempAns;
                                while (TempOper.get(operIdx).equals("-") || TempOper.get(operIdx).equals("+")) {
                                    if (TempOper.get(operIdx).equals("-")) {
                                        preAns = TempNum.get(numIdx) - preAns;
                                    } else if (TempOper.get(operIdx).equals("+")) {
                                        preAns = TempNum.get(numIdx) + preAns;
                                    }
                                    TempNum.remove(numIdx);
                                    TempOper.remove(operIdx);
                                    operIdx = TempOper.size() - 1;
                                    numIdx = TempNum.size() - 1;
                                    if (numIdx < 0 || operIdx < 0)
                                        break;
                                }
                                tempAns = preAns;
                            }
                        }

                        formatPrint();
                        if(decimalFormat != null)
                            answerText.setText(decimalFormat.format(tempAns));
                        else
                            answerText.setText("" + tempAns);
                        TempNum.add(tempAns);
                        tempAns = 0;
                        TempOper.add("-");
                        return;
                    case R.id.AddButton:
                        TempNum.clear();
                        TempOper.clear();
                        TempNum.addAll(Num);
                        TempOper.addAll(Oper);
                        tempAns = ans;

                        press = true;

                        if(TempOper.size() == 0)
                        {
                            TempNum.add(tempAns);
                            tempAns = 0;
                            TempOper.add("+");
                            return;
                        }

                        while(TempOper.size() != 0 || TempNum.size() != 0)
                        {
                            if (TempOper.get(TempOper.size() - 1).equals("/") || TempOper.get(TempOper.size() - 1).equals("*")) {
                                double preAns = tempAns;
                                if (TempOper.get(TempOper.size() - 1).equals("/")) {
                                    preAns = TempNum.get(TempNum.size() - 1) / preAns;
                                } else if (TempOper.get(TempOper.size() - 1).equals("*")) {
                                    preAns = TempNum.get(TempNum.size() - 1) * preAns;
                                }
                                TempNum.remove(TempNum.size() - 1);
                                TempOper.remove(TempOper.size() - 1);
                                tempAns = preAns;
                            } else {
                                int operIdx = TempOper.size() - 1;
                                int numIdx = TempNum.size() - 1;
                                double preAns = tempAns;
                                while (TempOper.get(operIdx).equals("-") || TempOper.get(operIdx).equals("+")) {
                                    if (TempOper.get(operIdx).equals("-")) {
                                        preAns = TempNum.get(numIdx) - preAns;
                                    } else if (TempOper.get(operIdx).equals("+")) {
                                        preAns = TempNum.get(numIdx) + preAns;
                                    }
                                    TempNum.remove(numIdx);
                                    TempOper.remove(operIdx);
                                    operIdx = TempOper.size() - 1;
                                    numIdx = TempNum.size() - 1;
                                    if (numIdx < 0 || operIdx < 0)
                                        break;
                                }
                                tempAns = preAns;
                            }
                        }
                        formatPrint();
                        if(decimalFormat != null)
                            answerText.setText(decimalFormat.format(tempAns));
                        else
                            answerText.setText("" + tempAns);
                        TempNum.add(tempAns);
                        tempAns = 0;
                        TempOper.add("+");
                        return;
                    case R.id.AnswerButton:
                        ans = Double.parseDouble(answerText.getText().toString());
                        Num.clear();
                        Oper.clear();
                        Num.addAll(TempNum);
                        Oper.addAll(TempOper);

                        press = true;

                        while(Oper.size() != 0 || Num.size() != 0)
                        {
                            if (Oper.get(Oper.size() - 1).equals("/") || Oper.get(Oper.size() - 1).equals("*")) {
                                double preAns = ans;
                                if (Oper.get(Oper.size() - 1).equals("/")) {
                                    preAns = Num.get(Num.size() - 1) / preAns;
                                } else if (Oper.get(Oper.size() - 1).equals("*")) {
                                    preAns = Num.get(Num.size() - 1) * preAns;
                                }
                                Num.remove(Num.size() - 1);
                                Oper.remove(Oper.size() - 1);
                                ans = preAns;
                            } else {
                                int operIdx = Oper.size() - 1;
                                int numIdx = Num.size() - 1;
                                double preAns = ans;
                                while (Oper.get(operIdx).equals("-") || Oper.get(operIdx).equals("+")) {
                                    if (Oper.get(operIdx).equals("-")) {
                                        preAns = Num.get(numIdx) - preAns;
                                    } else if (Oper.get(operIdx).equals("+")) {
                                        preAns = Num.get(numIdx) + preAns;
                                    }
                                    Num.remove(numIdx);
                                    Oper.remove(operIdx);
                                    operIdx = Oper.size() - 1;
                                    numIdx = Num.size() - 1;
                                    if (numIdx < 0 || operIdx < 0)
                                        break;
                                }
                                ans = preAns;
                            }
                        }
                        formatPrint();
                        if(decimalFormat != null)
                            answerText.setText(decimalFormat.format(ans));
                        else
                            answerText.setText("" + ans);

                        Num.clear();
                        Oper.clear();
                        TempNum.clear();
                        TempOper.clear();
                        return;
                    case R.id.DotButton:
                        preNum = answerText.getText().toString();
                        if(preNum.length() == length && !press)
                        {
                            press = false;
                            return;
                        }
                        for(int i = 0; i < preNum.length(); i++)
                        {
                            if(preNum.charAt(i) == '.')
                                return;
                        }
                        if(press)
                            answerText.setText("0.");
                        else
                            answerText.setText(preNum + ".");
                        press = false;
                        return;
                    case R.id.Button0:
                        if(press)
                        {
                            Num.clear();
                            Oper.clear();
                            Num.addAll(TempNum);
                            Oper.addAll(TempOper);
                            ans = tempAns;
                        }

                        preNum = answerText.getText().toString();
                        if(preNum.length() == length && !press)
                        {
                            press = false;
                            return;
                        }
                        if(preNum.equals("0") || press)
                            answerText.setText("0");
                        else
                            answerText.setText(preNum + "0");
                        nowNum = answerText.getText().toString();
                        ans = Double.parseDouble(nowNum);
                        press = false;
                        return;
                    case R.id.Button1:
                        if(press)
                        {
                            Num.clear();
                            Oper.clear();
                            Num.addAll(TempNum);
                            Oper.addAll(TempOper);
                            ans = tempAns;
                        }

                        preNum = answerText.getText().toString();
                        if(preNum.length() == length && !press)
                        {
                            press = false;
                            return;
                        }
                        if(preNum.equals("0") || press)
                            answerText.setText("1");
                        else
                            answerText.setText(preNum + "1");
                        nowNum = answerText.getText().toString();
                        ans = Double.parseDouble(nowNum);
                        press = false;
                        return;
                    case R.id.Button2:
                        if(press)
                        {
                            Num.clear();
                            Oper.clear();
                            Num.addAll(TempNum);
                            Oper.addAll(TempOper);
                            ans = tempAns;
                        }

                        preNum = answerText.getText().toString();
                        if(preNum.length() == length && !press)
                        {
                            press = false;
                            return;
                        }
                        if(preNum.equals("0") || press)
                            answerText.setText("2");
                        else
                            answerText.setText(preNum + "2");
                        nowNum = answerText.getText().toString();
                        ans = Double.parseDouble(nowNum);
                        press = false;
                        return;
                    case R.id.Button3:
                        if(press)
                        {
                            Num.clear();
                            Oper.clear();
                            Num.addAll(TempNum);
                            Oper.addAll(TempOper);
                            ans = tempAns;
                        }

                        preNum = answerText.getText().toString();
                        if(preNum.length() == length && !press)
                        {
                            press = false;
                            return;
                        }
                        if(preNum.equals("0") || press)
                            answerText.setText("3");
                        else
                            answerText.setText(preNum + "3");
                        nowNum = answerText.getText().toString();
                        ans = Double.parseDouble(nowNum);
                        press = false;
                        return;
                    case R.id.Button4:
                        if(press)
                        {
                            Num.clear();
                            Oper.clear();
                            Num.addAll(TempNum);
                            Oper.addAll(TempOper);
                            ans = tempAns;
                        }

                        preNum = answerText.getText().toString();
                        if(preNum.length() == length && !press)
                        {
                            press = false;
                            return;
                        }
                        if(preNum.equals("0") || press)
                            answerText.setText("4");
                        else
                            answerText.setText(preNum + "4");
                        nowNum = answerText.getText().toString();
                        ans = Double.parseDouble(nowNum);
                        press = false;
                        return;
                    case R.id.Button5:
                        if(press)
                        {
                            Num.clear();
                            Oper.clear();
                            Num.addAll(TempNum);
                            Oper.addAll(TempOper);
                            ans = tempAns;
                        }

                        preNum = answerText.getText().toString();
                        if(preNum.length() == length && !press)
                        {
                            press = false;
                            return;
                        }
                        if(preNum.equals("0") || press)
                            answerText.setText("5");
                        else
                            answerText.setText(preNum + "5");
                        nowNum = answerText.getText().toString();
                        ans = Double.parseDouble(nowNum);
                        press = false;
                        return;
                    case R.id.Button6:
                        if(press)
                        {
                            Num.clear();
                            Oper.clear();
                            Num.addAll(TempNum);
                            Oper.addAll(TempOper);
                            ans = tempAns;
                        }

                        preNum = answerText.getText().toString();
                        if(preNum.length() == length && !press)
                        {
                            press = false;
                            return;
                        }
                        if(preNum.equals("0") || press)
                            answerText.setText("6");
                        else
                            answerText.setText(preNum + "6");
                        nowNum = answerText.getText().toString();
                        ans = Double.parseDouble(nowNum);
                        press = false;
                        return;
                    case R.id.Button7:
                        if(press)
                        {
                            Num.clear();
                            Oper.clear();
                            Num.addAll(TempNum);
                            Oper.addAll(TempOper);
                            ans = tempAns;
                        }

                        preNum = answerText.getText().toString();
                        if(preNum.length() == length && !press)
                        {
                            press = false;
                            return;
                        }
                        if(preNum.equals("0") || press)
                            answerText.setText("7");
                        else
                            answerText.setText(preNum + "7");
                        nowNum = answerText.getText().toString();
                        ans = Double.parseDouble(nowNum);
                        press = false;
                        return;
                    case R.id.Button8:
                        if(press)
                        {
                            Num.clear();
                            Oper.clear();
                            Num.addAll(TempNum);
                            Oper.addAll(TempOper);
                            ans = tempAns;
                        }

                        preNum = answerText.getText().toString();
                        if(preNum.length() == length && !press)
                        {
                            press = false;
                            return;
                        }
                        if(preNum.equals("0") || press)
                            answerText.setText("8");
                        else
                            answerText.setText(preNum + "8");
                        nowNum = answerText.getText().toString();
                        ans = Double.parseDouble(nowNum);
                        press = false;
                        return;
                    case R.id.Button9:
                        if(press)
                        {
                            Num.clear();
                            Oper.clear();
                            Num.addAll(TempNum);
                            Oper.addAll(TempOper);
                            ans = tempAns;
                        }

                        preNum = answerText.getText().toString();
                        if(preNum.length() == length && !press)
                        {
                            press = false;
                            return;
                        }
                        if(preNum.equals("0") || press)
                            answerText.setText("9");
                        else
                            answerText.setText(preNum + "9");
                        nowNum = answerText.getText().toString();
                        ans = Double.parseDouble(nowNum);
                        press = false;
                        return;
                }
            }
        };

        clearButton .setOnClickListener(buttonListener);
        negationButton .setOnClickListener(buttonListener);
        percentButton .setOnClickListener(buttonListener);
        divideButton .setOnClickListener(buttonListener);
        multiplyButton .setOnClickListener(buttonListener);
        minusButton .setOnClickListener(buttonListener);
        addButton .setOnClickListener(buttonListener);
        answerButton .setOnClickListener(buttonListener);
        dotButton .setOnClickListener(buttonListener);
        button0 .setOnClickListener(buttonListener);
        button1 .setOnClickListener(buttonListener);
        button2 .setOnClickListener(buttonListener);
        button3 .setOnClickListener(buttonListener);
        button4 .setOnClickListener(buttonListener);
        button5 .setOnClickListener(buttonListener);
        button6 .setOnClickListener(buttonListener);
        button7 .setOnClickListener(buttonListener);
        button8 .setOnClickListener(buttonListener);
        button9 .setOnClickListener(buttonListener);

    }
}
