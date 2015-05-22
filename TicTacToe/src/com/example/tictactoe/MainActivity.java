package com.example.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	int i, j, flag = 0, gamestatus = 0, cross = 0, dot = 0, drawflag = 0;
	int[][] A = { { '5', '5', '5' }, { '5', '5', '5' }, { '5', '5', '5' } };
	TextView t1;

	// MediaPlayer clipturn = MediaPlayer.create(MainActivity.this,
	// R.drawable.);

	// -----------LOGIC PART -----------

	public void win1() {
		t1.setText("Player 1 wins");
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("Player 1 wins")
				.setPositiveButton("Play Again",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								Intent intent = new Intent(
										"com.example.tictactoe.UtilityAct");
								startActivity(intent);
								finish();
							}
						})
				.setNegativeButton("Exit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						}).show();
	}

	public void win2() {
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("Player 2 wins")
				.setPositiveButton("Play Again",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								Intent intent = new Intent(
										"com.example.tictactoe.UtilityAct");
								startActivity(intent);
								finish();
							}
						})
				.setNegativeButton("Exit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						}).show();
	}

	public void draw() {
		t1.setText("Game Drawn");
		new AlertDialog.Builder(MainActivity.this)
				.setTitle("Player Drawn")
				.setPositiveButton("Play Again",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								Intent intent = new Intent(
										"com.example.tictactoe.UtilityAct");
								startActivity(intent);
								finish();
							}
						})
				.setNegativeButton("Exit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						}).show();
	}

	// Logic for horizontal checking (row wise)
	public void logic1() {
		for (i = 0; i <= 2; i++) {
			for (j = 0; j <= 2; j++) {
				if (A[i][j] == 1) {
					cross++; // to count number of cross
				} else if (A[i][j] == 0) {
					dot++; // to count the number of dot
				}
			} // when 1 row is over
			if (cross == 3) {

				win1();
			} else if (dot == 3) {

				win2();
			} else {
				if (gamestatus == 9) {
					drawflag = 1;
				}
			}
			cross = 0;
			dot = 0;
		}
	}

	// Logic for vertical checking (column wise) ,just reversing the loop
	public void logic2() {
		for (j = 0; j <= 2; j++) {
			for (i = 0; i <= 2; i++) {
				if (A[i][j] == 1) {
					cross++; // to count number of cross
				} else if (A[i][j] == 0) {
					dot++; // to count the number of dot
				}
			} // when 1 row is over

			if (cross == 3) {

				win1();
			} else if (dot == 3) {

				win2();
			} else {
				if (gamestatus == 9) {
					drawflag = 1;
				}
			}
			cross = 0;
			dot = 0;
		}
	}

	// Logic for diagonal checking
	public void logic3() {
		// for first diagonal
		for (i = 0; i <= 2; i++) {
			for (j = 0; j <= 2; j++) {
				if (i == j) {
					if (A[i][j] == 1) {
						cross++; // to count number of cross
					} else if (A[i][j] == 0) {
						dot++; // to count the number of dot
					}
				}
			}
		}
		if (cross == 3) {

			win1();
		} else if (dot == 3) {

			win2();
		} else {
			if (gamestatus == 9) {
				drawflag = 1;
			}
		}
		cross = 0;
		dot = 0;

		// for second diagonal
		for (i = 0; i <= 2; i++) {
			for (j = 0; j <= 2; j++) {
				if (i + j == 2) {
					if (A[i][j] == 1) {
						cross++; // to count number of cross
					} else if (A[i][j] == 0) {
						dot++; // to count the number of dot
					}
				}
			}
		}
		if (cross == 3) {

			win1();
		} else if (dot == 3) {

			win2();
		} else {
			if (gamestatus == 9) {
				drawflag = 1;
			}
		}
		cross = 0;
		dot = 0;
		// code for game draw to check if flag of drawflag variable is 1
		if (drawflag == 1) {
			draw();
		}

	}

	// -----------LOGIC PART END--------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		t1 = (TextView) findViewById(R.id.tv);
		final LinearLayout l1 = (LinearLayout) findViewById(R.id.img1);
		final LinearLayout l2 = (LinearLayout) findViewById(R.id.img2);
		final LinearLayout l3 = (LinearLayout) findViewById(R.id.img3);
		final LinearLayout l4 = (LinearLayout) findViewById(R.id.img4);
		final LinearLayout l5 = (LinearLayout) findViewById(R.id.img5);
		final LinearLayout l6 = (LinearLayout) findViewById(R.id.img6);
		final LinearLayout l7 = (LinearLayout) findViewById(R.id.img7);
		final LinearLayout l8 = (LinearLayout) findViewById(R.id.img8);
		final LinearLayout l9 = (LinearLayout) findViewById(R.id.img9);

		l1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (flag == 0) {
					l1.setBackgroundResource(R.drawable.cross);
					flag = 1; // to check if first click is cross and other
								// click is dot
					A[0][0] = 1; // to input the cross and dot into array in
									// form of 1 and 0
					gamestatus++; // to count number of turns over
				} else {
					l1.setBackgroundResource(R.drawable.dot);
					flag = 0;
					A[0][0] = 0;
					gamestatus++;
				}

			}
		});

		l2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (flag == 0) {
					l2.setBackgroundResource(R.drawable.cross);
					flag = 1; // to check if first click is cross and other
								// click is dot
					A[0][1] = 1; // to input the cross and dot into array in
									// form of 1 and 0
					gamestatus++; // to count number of turns over
				} else {
					l2.setBackgroundResource(R.drawable.dot);
					flag = 0;
					A[0][1] = 0;
					gamestatus++;
				}
				logic1();
				logic2();
				logic3();
			}
		});

		l3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (flag == 0) {
					l3.setBackgroundResource(R.drawable.cross);
					flag = 1; // to check if first click is cross and other
								// click is dot
					A[0][2] = 1; // to input the cross and dot into array in
									// form of 1 and 0
					gamestatus++; // to count number of turns over
				} else {
					l3.setBackgroundResource(R.drawable.dot);
					flag = 0;
					A[0][2] = 0;
					gamestatus++;
				}
				logic1();
				logic2();
				logic3();
			}
		});

		l4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (flag == 0) {
					l4.setBackgroundResource(R.drawable.cross);
					flag = 1; // to check if first click is cross and other
								// click is dot
					A[1][0] = 1; // to input the cross and dot into array in
									// form of 1 and 0
					gamestatus++; // to count number of turns over
				} else {
					l4.setBackgroundResource(R.drawable.dot);
					flag = 0;
					A[1][0] = 0;
					gamestatus++;
				}
				logic1();
				logic2();
				logic3();
			}
		});

		l5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (flag == 0) {
					l5.setBackgroundResource(R.drawable.cross);
					flag = 1; // to check if first click is cross and other
								// click is dot
					A[1][1] = 1; // to input the cross and dot into array in
									// form of 1 and 0
					gamestatus++; // to count number of turns over
				} else {
					l5.setBackgroundResource(R.drawable.dot);
					flag = 0;
					A[1][1] = 0;
					gamestatus++;
				}
				logic1();
				logic2();
				logic3();
			}
		});

		l6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (flag == 0) {
					l6.setBackgroundResource(R.drawable.cross);
					flag = 1; // to check if first click is cross and other
								// click is dot
					A[1][2] = 1; // to input the cross and dot into array in
									// form of 1 and 0
					gamestatus++; // to count number of turns over
				} else {
					l6.setBackgroundResource(R.drawable.dot);
					flag = 0;
					A[1][2] = 0;
					gamestatus++;
				}
				logic1();
				logic2();
				logic3();
			}
		});

		l7.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (flag == 0) {
					l7.setBackgroundResource(R.drawable.cross);
					flag = 1; // to check if first click is cross and other
								// click is dot
					A[2][0] = 1; // to input the cross and dot into array in
									// form of 1 and 0
					gamestatus++; // to count number of turns over
				} else {
					l7.setBackgroundResource(R.drawable.dot);
					flag = 0;
					A[2][0] = 0;
					gamestatus++;
				}
				logic1();
				logic2();
				logic3();
			}
		});

		l8.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (flag == 0) {
					l8.setBackgroundResource(R.drawable.cross);
					flag = 1; // to check if first click is cross and other
								// click is dot
					A[2][1] = 1; // to input the cross and dot into array in
									// form of 1 and 0
					gamestatus++; // to count number of turns over
				} else {
					l8.setBackgroundResource(R.drawable.dot);
					flag = 0;
					A[2][1] = 0;
					gamestatus++;
				}
				logic1();
				logic2();
				logic3();
			}
		});

		l9.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (flag == 0) {
					l9.setBackgroundResource(R.drawable.cross);
					flag = 1; // to check if first click is cross and other
								// click is dot
					A[2][2] = 1; // to input the cross and dot into array in
									// form of 1 and 0
					gamestatus++; // to count number of turns over
				} else {
					l9.setBackgroundResource(R.drawable.dot);
					flag = 0;
					A[2][2] = 0;
					gamestatus++;
				}
				logic1();
				logic2();
				logic3();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
