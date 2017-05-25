package fintechnet.izxjf.com.qqstepview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final QQStepView qqStepView = (QQStepView) findViewById(R.id.qqStepView);
        qqStepView.setMaxProgress(100);
        qqStepView.setAnimatorProgress(55);
    }

}
