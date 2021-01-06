package cc.taylorzhang.formatedit.sample

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cc.taylorzhang.formatedit.sample.databinding.ActivityMainBinding
import cc.taylorzhang.formatedit.setOnFormatEditListener
import cc.taylorzhang.formatedit.setFormatRules

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2021/01/04
 *     desc   : Main
 *     version: 1.0.0
 * </pre>
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etPhone1.setFormatRules(3, 4, 4)
        binding.etPhone2.setFormatRules(3, 4, 4, formatChar = '-')
        binding.etIDNumber.setFormatRules(6, 4, 4, 4)

        binding.etPhone1.setOnFormatEditListener { isComplete, text ->
            if (isComplete) {
                Toast.makeText(this, text, Toast.LENGTH_SHORT).apply {
                    setGravity(Gravity.CENTER, 0, 0)
                    show()
                }
            }
        }
    }
}