package com.example.bai10;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Button btnStart;
    Handler handler = new Handler(); // để gửi dữ liệu về UI Thread

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> startFakeDownload());
    }

    private void startFakeDownload() {
        progressBar.setProgress(0); // reset progress

        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                int progress = i;
                handler.post(() -> progressBar.setProgress(progress)); // cập nhật UI

                try {
                    Thread.sleep(50); // giả lập tải chậm, tránh đơ UI
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Khi xong → thông báo Toast trên UI Thread
            handler.post(() ->
                    Toast.makeText(MainActivity.this, "Download hoàn tất!", Toast.LENGTH_SHORT).show()
            );
        }).start();
    }
}
