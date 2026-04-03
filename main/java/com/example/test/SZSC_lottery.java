package com.example.test;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SZSC_lottery extends AppCompatActivity {
    private static android.app.Activity activity=null;

    public static int diamonds_remain=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_szsc_lottery);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViewById(R.id.SZSC_apply_lottery_order_1).setOnClickListener(v -> {
            ButtonAction.Do_lottery_1.execute();
        });

        findViewById(R.id.SZSC_apply_lottery_order_10).setOnClickListener(v -> {
            ButtonAction.Do_lottery_10.execute();
        });
        activity=this;
        change_diamonds_remain(diamonds_remain);
    }
    private enum ButtonAction {

        Do_lottery_1 {
            @Override
            void execute() {

                SZSC_service.reply_set_parameter("order_kind",SZSC_protocol.SZSC_normal_order_1);
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_do_lottery);

            }
        },
        Do_lottery_10 {
            @Override
            void execute() {
                SZSC_service.reply_set_parameter("order_kind",SZSC_protocol.SZSC_normal_order_10);
                SZSC_service.reply_send_signal(SZSC_protocol.SZSC_apply_do_lottery);
            }
        };

        abstract void execute();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        activity = null;
    }


    // 供外部调用的静态方法,展示抽奖结果
    public static void addNetworkData(final JSON_process data) {
        if (activity != null) {
            if(activity.isDestroyed())
                return;
            final int total_mount=data.getInt("total_mount");
            diamonds_remain= data.getInt("diamonds_remain");
            activity.runOnUiThread(() -> change_diamonds_remain(diamonds_remain));

            activity.runOnUiThread(() -> addTableRow(Server_tools.get_current_time()+"  本次抽奖结果("+total_mount+"个):"));


            for(int i=0;i<total_mount;i++) {
                final int point=i;

                int kind=data.getIntFromList("kind", point);
                int effect_ID= data.getIntFromList("effect", point);

                activity.runOnUiThread(() ->
                        addTableRow(
                                "技能名:" +SZSC_EXCEL_process.get_name(effect_ID)+
                                "【种类】" + SZSC_EXCEL_process.lottory_result_get_quality(kind) +

                                        "\n【效果】"+SZSC_EXCEL_process.get_brief(effect_ID)
                        )
                );
            }

        }
    }

    private static void change_diamonds_remain(int value){
        android.widget.TextView diamons_remain_text=activity.findViewById(R.id.SZSC_lottery_diamonds_remain);
        diamons_remain_text.setText("钻石余额:"+value);
    }

    private static void addTableRow(String data) {
        android.widget.TableLayout room_log=activity.findViewById(R.id.SZSC_lottery_log);
        android.widget.TableRow row = new android.widget.TableRow(activity);

        // 数据文本
        android.widget.TextView textView = new android.widget.TextView(activity);
        textView.setText(data);
        textView.setTextColor(android.graphics.Color.BLACK);
        textView.setPadding(16, 16, 16, 16);

        row.addView(textView);
        room_log.addView(row);

        scrollToBottom(); // 添加新行后滚动到底部
    }
    private static void scrollToBottom() {
        android.widget.ScrollView scrollView=activity.findViewById(R.id.SZSC_lottery_log_Scrollview);
        scrollView.post(() -> scrollView.fullScroll(android.view.View.FOCUS_DOWN));
    }
}