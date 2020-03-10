package myexam.th.lth.newsapp.fragment;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import myexam.th.lth.newsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfileUser extends Fragment implements View.OnClickListener {

    private static final String TAG = "ProfileUser";
    private static final int RC_SIGN_IN = 101;

    private String name,email,pass,id;
    private SignInButton btnLoginGG;
    private Button btnLogoutGG;
    private TextView tvName_profile;

    ImageView ic_detailProfile,avtProfile;

    private GoogleSignInAccount mGoggle;
    private GoogleSignInClient mGoogleSignInClient;

    public FragmentProfileUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_profile_user, container, false );

        tvName_profile = (TextView)view.findViewById( R.id.tvName_profile );

        ic_detailProfile = (ImageView)view.findViewById( R.id.ic_detailProfile );
        //Google Login Services
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient( view.getContext() , gso );


        btnLoginGG = (SignInButton) view.findViewById( R.id.btnLoginGG );
        btnLogoutGG = (Button) view.findViewById( R.id.btnLogoutGG );
        btnLoginGG.setSize( SignInButton.SIZE_STANDARD );

        btnLoginGG.setOnClickListener( this );
        btnLogoutGG.setOnClickListener( this );

        return view;
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }


//    private void getInfoLogin(View view){
//        mGoggle = GoogleSignIn.getLastSignedInAccount(view.getContext());
//        if (mGoggle != null) {
//            String mName = mGoggle.getDisplayName();
//            String mGivenName = mGoggle.getGivenName();
//            String mFamilyName = mGoggle.getFamilyName();
//
//            String mFullname = mFamilyName + mName;
//            String mEmail = mGoggle.getEmail();
//            String mId = mGoggle.getId();
//            tvName_profile.setText( mGoggle.getFamilyName()+" "+mGoggle.getDisplayName() );
//
////            Toast.makeText( view.getContext(), mFullname+"\n"+mEmail,Toast.LENGTH_SHORT  ).show();
//        }else
//            Log.d( TAG,"LOGIN FAIL!" );
//
//    }

    private void getInfo(GoogleSignInAccount account) {
        if (account != null){
            email = account.getEmail();
            //Get Google infomation
//            Toast.makeText(LoginActivity.this,
//                    account.getDisplayName() + "\n"+
//                            account.getGivenName() + "\n"+
//                            account.getFamilyName() + "\n"+
//                            account.getEmail() + "\n"+
//                            account.getId(), LENGTH_LONG).show();
        } else {
            Log.d(TAG, "Not logged in");
        }
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.btnLoginGG:
                signIn();

                break;
            case R.id.btnLogoutGG:
                signOut();
                tvName_profile.setText( "Đọc giả" );
                btnLoginGG.setVisibility( View.VISIBLE );
                btnLogoutGG.setVisibility( View.GONE );
                ic_detailProfile.setVisibility( View.GONE );
                break;
        }
    }

    private void checkLoginSuccess(){
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Đang xác nhận",
                "Vui lòng chờ...", true);
        dialog.show();

        //authentication from server
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess
//                        getInfoLogin(getView());
                        btnLoginGG.setVisibility( View.GONE );
                        btnLogoutGG.setVisibility( View.VISIBLE );
                        ic_detailProfile.setVisibility( View.VISIBLE );

                        dialog.dismiss();
                    }
                }, 2000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 101) {
            // The Task returned from this call is always completed, no need to attach a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

            checkLoginSuccess();
        }else {
            Log.i( TAG,"Login Fail"+requestCode );
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            getInfo(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "failed code: " + e.getStatusCode());
            btnLoginGG.setVisibility( View.VISIBLE );
            btnLogoutGG.setVisibility( View.GONE );
            ic_detailProfile.setVisibility( View.GONE );
            getInfo(null);
        }
    }

}
