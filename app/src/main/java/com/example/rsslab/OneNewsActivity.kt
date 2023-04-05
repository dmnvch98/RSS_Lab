import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.rsslab.R
import com.example.rsslab.databinding.ActivityOneNewsBinding
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import org.sufficientlysecure.htmltextview.HtmlTextView
class OneNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOneNewsBinding

    var title = ""
    var date = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_one_news)

        binding = ActivityOneNewsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val author = intent.getStringExtra("AUTHOR")
//        this.toolbar_one.title = "Author - \n$author"
//        setSupportActionBar(toolbar_one)
        title = intent.getStringExtra("TITLE").toString()
        date = intent.getStringExtra("DATE").toString()
        val content = intent.getStringExtra("CONTENT")
        binding.oneTextTitle.text = title
//        this.oneTextTitle.text = title
        binding.oneTextPubdate.text = date
//        this.oneTextPubdate.text = date
        val textView = findViewById<View>(R.id.html_text) as HtmlTextView
        textView.setHtml(content.toString(), HtmlHttpImageGetter(textView))
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.one_page_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.shareBtn) {
            shareData()
        }
        return true
    }
    private fun shareData() {
        //concatenate
        val s = title + "\n" + date
        //share intent
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, s)
        startActivity(Intent.createChooser(shareIntent, s))
    }
}