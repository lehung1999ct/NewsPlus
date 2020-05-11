package myexam.th.lth.newsapp.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import myexam.th.lth.newsapp.R;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    TextView tvAboutUs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_info );

        tvAboutUs = findViewById( R.id.tvAboutUs );
        Toolbar toolbar_weather = (Toolbar) findViewById(R.id.toolbar_info);
        setSupportActionBar(toolbar_weather);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle( "About Us" );

        String htt = "<html>" +
                "<p><b>Ứng dụng đọc tin tức News Plus</b></p><br>" +
                "<p>Cám ơn bạn đã sử dụng ứng dụng của chúng tôi.</p><br>" +
                "<p>Chúng tôi sẽ tiếp tục cập nhật sản phẩm để mang lại trải nghiệm tốt nhất cho các bạn</p>\n" +
                "<p>Hiện tại ứng dụng vẫn còn trong gia đoạn phát triển nên vẫn có thể xảy ra 1 số lỗi đáng tiếc không mong muốn, mong bạn thông cảm và vui lòng góp ý về hòm thư: <a href=\"#\">lehung@gmail.com</a></p><br>"+
                "<p>Xin chân thành cám ơn sự hợp tác của các bạn.</p>\n" +
                "\n <br>" + "</html>";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvAboutUs.setText( Html.fromHtml(htt, Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvAboutUs.setText(Html.fromHtml(htt));
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected( item );
    }
}
