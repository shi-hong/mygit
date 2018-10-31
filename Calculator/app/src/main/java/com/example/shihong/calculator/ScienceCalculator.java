package com.example.shihong.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScienceCalculator extends Activity {

    private StringBuilder str= new StringBuilder();
    public void roll(String s ,TextView txt) {
        txt.append(s);
        int scrollAmount = txt.getLayout().getLineTop(txt.getLineCount())
                - txt.getHeight();
        if (scrollAmount > 0)
            txt.scrollTo(0, scrollAmount);
        else
            txt.scrollTo(0, 0);
    }
    public boolean check1(){
        if(str.length()!=0)
                return true;
        return false;
    }
    public boolean check2(){
        if(str.length()!=0) {
            if(str.indexOf(".") == -1 && str.charAt(str.length()-1) >= '0' && str.charAt(str.length()-1) <= '9')
                    return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_calculator);
        final TextView txtResult=(TextView)findViewById(R.id.textResult);

        Button btnClear=(Button)findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=str.delete(0,str.length());
                txtResult.setText("");
            }
        });

        Button btnDelete=(Button)findViewById(R.id.buttonDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str.length() != 0) {
                    str = str.deleteCharAt(str.length()-1);
                    txtResult.setText(str);
                }
            }
        });

        Button btnOne=(Button)findViewById(R.id.buttonOne);
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=str.append("1");
                txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                txtResult.post(new Runnable() {
                    @Override
                    public void run() {
                        roll("1",txtResult);
                    }
                });
            }
        });

        Button btnTwo=(Button)findViewById(R.id.buttonTwo);
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=str.append("2");
                txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                txtResult.post(new Runnable() {
                    @Override
                    public void run() {
                        roll("2",txtResult);
                    }
                });
            }
        });

        Button btnThree=(Button)findViewById(R.id.buttonThree);
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=str.append("3");
                txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                txtResult.post(new Runnable() {
                    @Override
                    public void run() {
                        roll("3",txtResult);
                    }
                });
            }
        });

        Button btnFour=(Button)findViewById(R.id.buttonFour);
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=str.append("4");
                txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                txtResult.post(new Runnable() {
                    @Override
                    public void run() {
                        roll("4",txtResult);
                    }
                });
            }
        });

        Button btnFive=(Button)findViewById(R.id.buttonFive);
        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=str.append("5");
                txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                txtResult.post(new Runnable() {
                    @Override
                    public void run() {
                        roll("5",txtResult);
                    }
                });
            }
        });

        Button btnSix=(Button)findViewById(R.id.buttonSix);
        btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=str.append("6");
                txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                txtResult.post(new Runnable() {
                    @Override
                    public void run() {
                        roll("6",txtResult);
                    }
                });
            }
        });

        Button btnSeven=(Button)findViewById(R.id.buttonSeven);
        btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=str.append("7");
                txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                txtResult.post(new Runnable() {
                    @Override
                    public void run() {
                        roll("7",txtResult);
                    }
                });
            }
        });

        Button btnEight=(Button)findViewById(R.id.buttonEight);
        btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=str.append("8");
                txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                txtResult.post(new Runnable() {
                    @Override
                    public void run() {
                        roll("8",txtResult);
                    }
                });
            }
        });

        Button btnNine=(Button)findViewById(R.id.buttonNine);
        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=str.append("9");
                txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                txtResult.post(new Runnable() {
                    @Override
                    public void run() {
                        roll("9",txtResult);
                    }
                });
            }
        });

        Button btnZero=(Button)findViewById(R.id.buttonZero);
        btnZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str=str.append("0");
                txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                txtResult.post(new Runnable() {
                    @Override
                    public void run() {
                        roll("0",txtResult);
                    }
                });
            }
        });

        Button btnPoint=(Button)findViewById(R.id.buttonPoint);
        btnPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check2()) {
                    str = str.append(".");
                    txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            roll(".", txtResult);
                        }
                    });
                }
            }
        });

        Button btnSin=(Button)findViewById(R.id.buttonSin);
        btnSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()) {
                    Calculator cal = new Calculator();
                    String result;
                    try {
                        result = cal.scienceCalculate(str,"sin");
                    } catch (Exception ex) {
                        result = "运算失败";
                    }
                    txtResult.setText("sin" + str + "\n" + "＝" + result);
                    str=new StringBuilder(result);
                }
            }
        });

        Button btnCos=(Button)findViewById(R.id.buttonCos);
        btnCos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()) {
                    Calculator cal = new Calculator();
                    String result;
                    try {
                        result = cal.scienceCalculate(str,"cos");
                    } catch (Exception ex) {
                        result = "运算失败";
                    }
                    txtResult.setText("cos" + str + "\n" + "＝" + result);
                    str=new StringBuilder(result);
                }
            }
        });

        Button btnTan=(Button)findViewById(R.id.buttonTan);
        btnTan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()) {
                    Calculator cal = new Calculator();
                    String result;
                    try {
                        result = cal.scienceCalculate(str,"tan");
                    } catch (Exception ex) {
                        result = "运算失败";
                    }
                    txtResult.setText("tan" + str + "\n" + "＝" + result);
                    str=new StringBuilder(result);
                }
            }
        });

        Button btnLog=(Button)findViewById(R.id.buttonLog);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()) {
                    Calculator cal = new Calculator();
                    String result;
                    try {
                        result = cal.scienceCalculate(str,"log");
                    } catch (Exception ex) {
                        result = "运算失败";
                    }
                    txtResult.setText("log"+str + "\n" + "＝" + result);
                    str=new StringBuilder(result);
                }
            }
        });

        Button btnLn=(Button)findViewById(R.id.buttonLn);
        btnLn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()) {
                    Calculator cal = new Calculator();
                    String result;
                    try {
                        result = cal.scienceCalculate(str,"ln");
                    } catch (Exception ex) {
                        result = "运算失败";
                    }
                    txtResult.setText("ln"+str + "\n" + "＝" + result);
                    str=new StringBuilder(result);
                }
            }
        });

        Button btnPf=(Button)findViewById(R.id.buttonPf);
        btnPf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()) {
                    Calculator cal = new Calculator();
                    String result;
                    try {
                        result = cal.scienceCalculate(str,"²");
                    } catch (Exception ex) {
                        result = "运算失败";
                    }
                    txtResult.setText(str + "²" + "\n" + "＝" + result);
                    str=new StringBuilder(result);
                }
            }
        });

        Button btnLf=(Button)findViewById(R.id.buttonLf);
        btnLf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()) {
                    Calculator cal = new Calculator();
                    String result;
                    try {
                        result = cal.scienceCalculate(str,"³");
                    } catch (Exception ex) {
                        result = "运算失败";
                    }
                    txtResult.setText(str + "³" + "\n" + "＝" + result);
                    str=new StringBuilder(result);
                }
            }
        });

        Button btnKf=(Button)findViewById(R.id.buttonKf);
        btnKf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()) {
                    Calculator cal = new Calculator();
                    String result;
                    try {
                        result = cal.scienceCalculate(str,"√");
                    } catch (Exception ex) {
                        result = "运算失败";
                    }
                    txtResult.setText("√"+str + "\n" + "＝" + result);
                    str=new StringBuilder(result);
                }
            }
        });

        Button btnFact=(Button)findViewById(R.id.buttonFact);
        btnFact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()) {
                    Calculator cal = new Calculator();
                    String result;
                    try {
                        result = cal.scienceCalculate(str,"!");
                    } catch (Exception ex) {
                        result = "运算失败";
                    }
                    txtResult.setText(str + "!" + "\n" + "＝" + result);
                    str=new StringBuilder(result);
                }
            }
        });

        Button btnTenToTwo=(Button)findViewById(R.id.buttonTenToTwo);
        btnTenToTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()) {
                    Calculator cal = new Calculator();
                    String result;
                    try {
                        result = cal.scienceCalculate(str,"TenToTwo");
                    } catch (Exception ex) {
                        result = "运算失败";
                    }
                    txtResult.setText(str + "\n" + "＝" + "(" + result + ")₂");
                    str=new StringBuilder(result);
                }
            }
        });

        Button btnTwoToTen=(Button)findViewById(R.id.buttonTwoToTen);
        btnTwoToTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()) {
                    Calculator cal = new Calculator();
                    String result;
                    try {
                        result = cal.scienceCalculate(str,"TwoToTen");
                    } catch (Exception ex) {
                        result = "运算失败";
                    }
                    txtResult.setText("(" + str + ")₂" + "\n" + "＝" + result);
                    str=new StringBuilder(result);
                }
            }
        });

        Button btnTenToSixteen=(Button)findViewById(R.id.buttonTenToSixteen);
        btnTenToSixteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()) {
                    Calculator cal = new Calculator();
                    String result;
                    try {
                        result = cal.scienceCalculate(str,"TenToSixteen");
                    } catch (Exception ex) {
                        result = "运算失败";
                    }
                    txtResult.setText(str + "\n" + "＝" + "(" + result + ")₁₆");
                    str=new StringBuilder(result);
                }
            }
        });

        Button btnTwoToSixteen=(Button)findViewById(R.id.buttonTwoToSixteen);
        btnTwoToSixteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1()) {
                    Calculator cal = new Calculator();
                    String result;
                    try {
                        result = cal.scienceCalculate(str,"TwoToSixteen");
                    } catch (Exception ex) {
                        result = "运算失败";
                    }
                    txtResult.setText("(" + str + ")₂" + "\n" + "＝" + "(" + result + ")₁₆");
                    str=new StringBuilder(result);
                }
            }
        });

        Button btnReturn=(Button)findViewById(R.id.buttonReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScienceCalculator.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
