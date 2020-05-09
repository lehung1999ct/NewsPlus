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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import myexam.th.lth.newsapp.R;
import myexam.th.lth.newsapp.adapter.BookmarkAdapter;
import myexam.th.lth.newsapp.adapter.SeenAdapter;
import myexam.th.lth.newsapp.dao.NewsDAO;
import myexam.th.lth.newsapp.dao.NewsSeenDAO;
import myexam.th.lth.newsapp.model.BookmarkNews;
import myexam.th.lth.newsapp.model.NewsSeen;
import myexam.th.lth.newsapp.screen.BookmarkActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfileUser extends Fragment implements View.OnClickListener {

    private static final String TAG = "ProfileUser";
    private static final int RC_SIGN_IN = 101;

    private String name,email,pass,id,avt;
    private SignInButton btnLoginGG;
    private ImageButton btnLogoutGG,btnBookMark_profile;
    private TextView tvName_profile;
    private LinearLayout itemLogout;

    ImageView avtProfile;

    private GoogleSignInAccount mGoggle;
    private GoogleSignInClient mGoogleSignInClient;


    NewsSeenDAO dao;
    ArrayList<NewsSeen> arrayList;
    private RecyclerView rvSeen_profile;
    private SeenAdapter adapter;


    public FragmentProfileUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_profile_user, container, false );
        tvName_profile = (TextView)view.findViewById( R.id.tvName_profile );
        avtProfile = (ImageView)view.findViewById( R.id.ivThumbUser );
        itemLogout = (LinearLayout)view.findViewById( R.id.itemLogout );

        rvSeen_profile = (RecyclerView)view.findViewById( R.id.rvSeen_profile );

        init();
        //Google Login Services
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient( view.getContext() , gso );


        btnLoginGG = (SignInButton) view.findViewById( R.id.btnLoginGG );
        btnLogoutGG = (ImageButton) view.findViewById( R.id.btnLogoutGG );
        btnBookMark_profile = (ImageButton) view.findViewById( R.id.btnBookMark_profile );
        btnLoginGG.setSize( SignInButton.SIZE_STANDARD );

        btnLoginGG.setOnClickListener( this );
        btnBookMark_profile.setOnClickListener( this );
        btnLogoutGG.setOnClickListener( this );


        LinearLayoutManager manager = new LinearLayoutManager( getContext(),RecyclerView.VERTICAL,false );

        rvSeen_profile.setLayoutManager( manager );
        rvSeen_profile.setAdapter( adapter );
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void init(){

        dao = NewsSeenDAO.getInstance( getContext() );
        arrayList=dao.getBookmarkAll();
        Collections.sort( arrayList, NewsSeen.comparatorNews );
        adapter = new SeenAdapter( getContext(),arrayList );
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

    private void getInfo(GoogleSignInAccount account) {
        if (account != null){
            email = account.getEmail();
            avt = String.valueOf( account.getPhotoUrl() );
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
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Đang đăng xuất",
                        "Vui lòng chờ...", true);
                dialog.show();
                new Handler(  ).postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        signOut();
                        tvName_profile.setText( "Đọc giả" );
                        btnLoginGG.setVisibility( View.VISIBLE );
                        itemLogout.setVisibility( View.GONE );
                        avtProfile.setImageResource( R.drawable.ic_user );
                        dialog.dismiss();
                    }

                }, 1500 );
                break;

            case R.id.btnBookMark_profile:
                startActivity( new Intent( v.getContext(), BookmarkActivity.class ) );
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
                        itemLogout.setVisibility( View.VISIBLE );
                        getInfo( mGoggle );
//                        Toast.makeText( getContext(), "thumb: "+avt, Toast.LENGTH_SHORT ).show();
                        tvName_profile.setText( email );
                        Glide.with( getContext() ).load( avt ).placeholder( R.drawable.ic_user ).into( avtProfile );
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
            itemLogout.setVisibility( View.GONE );
            getInfo(null);
        }
    }

}
