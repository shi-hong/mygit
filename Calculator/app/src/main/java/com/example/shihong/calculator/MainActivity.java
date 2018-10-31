package com.example.shihong.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

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
        if(str.length()!=0) {
            char c = str.charAt(str.length() - 1);
            if (c == '＋' || c == '－' || c == '×' || c == '÷' || c == '(')
                return false;
            else
                return true;
        }
        return false;
    }
    public boolean check2(){
        if(str.length()!=0) {
            if(str.charAt(str.length()-1) >= '0' && str.charAt(str.length()-1) <= '9') {
                int[] index = new int[5];
                index[0] = str.lastIndexOf("＋");
                index[1] = str.lastIndexOf("－");
                index[2] = str.lastIndexOf("×");
                index[3] = str.lastIndexOf("÷");
                index[4] = str.lastIndexOf(".");
                if (index[4] > index[3] && index[4] > index[2] && index[4] > index[1] && index[4] > index[0])
                    return false;
                else
                    return true;
            }
        }
        return false;
    }
    public boolean check3(){
        if(str.length()!=0){
            int n=0,m=0,index=0;
            while(str.indexOf("(",index)!=-1){
                n++;
                index=str.indexOf("(",index)+1;
            }
            index=0;
            while(str.indexOf(")",index)!=-1){
                m++;
                index=str.indexOf(")",index)+1;
            }
            if(m<=n-1)
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView txtResult=(TextView)findViewById(R.id.textResult);

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

        Button btnAdd=(Button)findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check1()){
                    str=str.append("＋");
                    txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            roll("＋",txtResult);
                        }
                    });
                }
            }
        });

        Button btnMinus=(Button)findViewById(R.id.buttonMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check1()) {
                    str = str.append("－");
                    txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            roll("－", txtResult);
                        }
                    });
                }
            }
        });

        Button btnMul=(Button)findViewById(R.id.buttonMul);
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check1()) {
                    str = str.append("×");
                    txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            roll("×", txtResult);
                        }
                    });
                }
            }
        });

        Button btnDiv=(Button)findViewById(R.id.buttonDiv);
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check1()) {
                    str = str.append("÷");
                    txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            roll("÷", txtResult);
                        }
                    });
                }
            }
        });

        Button btnLeft=(Button)findViewById(R.id.buttonLeft);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = str.append("(");
                txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                txtResult.post(new Runnable() {
                    @Override
                    public void run() {
                        roll("(", txtResult);
                    }
                });
            }
        });

        Button btnRight=(Button)findViewById(R.id.buttonRight);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check3()) {
                    str = str.append(")");
                    txtResult.setMovementMethod(ScrollingMovementMethod.getInstance());
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            roll(")", txtResult);
                        }
                    });
                }
            }
        });

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

        Button btnEqual=(Button)findViewById(R.id.buttonEqual);
        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((str.length() != 0)) {
                    Calculator cal = new Calculator();
                    String result;
                    try {
                        result = cal.calculateResult(cal.infixToSuffix(str));
                    } catch (Exception ex) {
                        result = "运算失败";
                    }
                    txtResult.setText(str + "\n" + "＝" + result);
                    str=new StringBuilder(result);
                }
            }
        });

        Button btnJump=(Button)findViewById(R.id.buttonJump);
        btnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScienceCalculator.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
