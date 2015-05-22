package com.example.cricketscorebook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondInningscorebook extends Activity {

	Button oneadd ,onesubtract ,twoadd ,twosubtract ,fouradd ,foursubtract ,sixadd ;
	Button sixsubtract ,balladd ,ballsubtract ,wicketadd ,wicketsubtract;
	TextView score ,runrate ,overs ,overlimit ,runstowin ,reqrate;
	int overlimits ,balllimit ,scorecount=0 , wicketcount=0 ,balls=0 ,overint=0 ,overballs=0 ,runleft=0 ,ballleft =0 ,chasescore=0;
	float runratecount ,reqratecount;
	//float runratecount=0 ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondinningscorebook);
		
		score = (TextView) findViewById(R.id.score);
		runrate = (TextView) findViewById(R.id.runrate);
		overs = (TextView) findViewById(R.id.overs);	
		overlimit = (TextView) findViewById(R.id.overlimit);
		runstowin = (TextView) findViewById(R.id.runstowin);
		reqrate = (TextView) findViewById(R.id.reqrate);
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
		chasescore = Integer.parseInt(getIntent().getExtras().getString("chasescore"));     // storing the chasescore
		balllimit = overlimits*6;
		reqratecount = (float)(chasescore*6)/balllimit;
		String nreqratecount = String.format("%.2f", reqratecount);
		reqrate.setText("Req Run Rate "+nreqratecount);
		overlimit.setText("(" +(String.valueOf(overlimits)) + ")");
		runstowin.setText("Need "+String.valueOf(chasescore)+ " runs to win from "+String.valueOf(balllimit)+ " balls");
		
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

		overint = balls/6;
		overballs = balls%6;
		runleft = chasescore - scorecount;
		ballleft = balllimit - balls;
		if(balls !=0){
		runratecount = (float)(scorecount*6)/balls;	
		reqratecount = (float)(runleft*6)/ballleft;
		}
		String nrunratecount = String.format("%.2f", runratecount);		
		String nreqratecount = String.format("%.2f", reqratecount);

		
		score.setText(String.valueOf(scorecount) +"/0");
		runrate.setText(nrunratecount);
		reqrate.setText("Req Run Rate "+nreqratecount);
		overs.setText(String.valueOf(overint) +"."+ String.valueOf(overballs));
		runstowin.setText("Need "+String.valueOf(runleft)+ " runs to win from "+String.valueOf(ballleft)+ " balls");
		

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}