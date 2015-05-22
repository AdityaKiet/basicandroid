package com.example.cricketscorebook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstInningscorebook extends Activity {

	Button oneadd ,onesubtract ,twoadd ,twosubtract ,fouradd ,foursubtract ,sixadd ;
	Button sixsubtract ,balladd ,ballsubtract ,wicketadd ,wicketsubtract;
	TextView score ,runrate ,overs ,overlimit ;
	int overlimits ,scorecount=0 , wicketcount=0 ,balls=0 ,overint=0 ,overballs=0;
	float runratecount;
	//float runratecount=0 ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.firstinningscorebook);
		
		score = (TextView) findViewById(R.id.score);
		runrate = (TextView) findViewById(R.id.runrate);
		overs = (TextView) findViewById(R.id.overs);	
		overlimit = (TextView) findViewById(R.id.overlimit);
		oneadd = (Button) findViewById(R.id.oneadd);
		onesubtract = (Button) findViewById(R.id.onesubtract);
		twoadd = (Button) findViewById(R.id.twoadd);
		twosubtract = (Button) findViewById(R.id.twosubtract);
		fouradd = (Button) findViewById(R.id.fouradd);
		foursubtract = (Button) findViewById(R.id.foursubtract);
		sixadd = (Button) findViewById(R.id.sixadd);
		sixsubtract = (Button) findViewById(R.id.sixsubtract);
		balladd = (Button) findViewById(R.id.balladd);
		ballsubtract = (Button) findViewById(R.id.ballsubtract);
		wicketadd = (Button) findViewById(R.id.wicketadd);
		wicketsubtract = (Button) findViewById(R.id.wicketsubtract);
		
		
		overlimits = Integer.parseInt(getIntent().getExtras().getString("overs"));			// storing the overlimit to variable
		overlimit.setText("(" +(String.valueOf(overlimits)) + ")");
		
		oneadd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				scorecount = scorecount+1;			
				scoreboard();
			}
		});
		balladd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				balls = balls+1;			
				scoreboard();
			}
		});
	}
	
	/*public void onClick(View v) {
		//method for on click on buttons
		if(v == oneadd) {
			scorecount = scorecount+1;
			score.setText(String.valueOf(scorecount) +"/0");			
			scoreboard();
		}
		if(v == twoadd) {
			scorecount = scorecount+2;
			scoreboard();
		}
		if(v == fouradd) {
			scorecount = scorecount+4;
			scoreboard();
		}
		if(v == sixadd) {
			scorecount = scorecount+6;
			scoreboard();
		}
		if(v == balladd) {
			balls = balls++;
			scoreboard();
		}
	}*/
	
	public void scoreboard() {

		if(balls !=0)
		runratecount = (float)(scorecount*6)/balls;
		overint = balls/6;
		overballs = balls%6;
		String nrunratecount = String.format("%.2f", runratecount);
		
		score.setText(String.valueOf(scorecount) +"/0");
		runrate.setText(nrunratecount);
		overs.setText(String.valueOf(overint) +"."+ String.valueOf(overballs));

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
