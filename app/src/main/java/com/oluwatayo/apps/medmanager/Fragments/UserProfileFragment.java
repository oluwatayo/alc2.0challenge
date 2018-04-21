package com.oluwatayo.apps.medmanager.Fragments;

import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.oluwatayo.apps.medmanager.LoginActivity;
import com.oluwatayo.apps.medmanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfileFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private View view;
    @BindView(R.id.user_fullName)
    TextView userFullName;
    @BindView(R.id.user_email)
    TextView userEmail;
    @BindView(R.id.user_dob)
    TextView userDob;
    @BindView(R.id.user_gender)
    TextView userGender;
    @BindView(R.id.avatar)
    ImageView avatar;

    SharedPreferences preferences;

    FirebaseUser user;

    public static UserProfileFragment NewInstance() {
        return new UserProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.bind(this, view);
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        user = FirebaseAuth.getInstance().getCurrentUser();
        setHasOptionsMenu(true);
        initUI(preferences);
        return view;
    }

    private void initUI(SharedPreferences prefs) {
        String dob = prefs.getString(LoginActivity.PREF_USER_DOB, "");
        String gender = prefs.getString(LoginActivity.PREF_USER_GENDER, "");
        userFullName.setText(user.getDisplayName());
        userDob.setText(TextUtils.isEmpty(dob) ? "Date Of Birth" : dob);
        userGender.setText(TextUtils.isEmpty(gender) ? "Gender" : gender);
        userEmail.setText(user.getEmail());

        if (TextUtils.isEmpty(gender) || TextUtils.isEmpty(dob)) {
            Toast.makeText(getContext(), "Your Profile is Incomplete", Toast.LENGTH_LONG).show();
        }
        avatar.setImageResource(gender.equals(EditProfileDialog.GENDER_MALE)?R.drawable.m11:R.drawable.f8);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_profile) {
            EditProfileDialog editProfileDialog = new EditProfileDialog();
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            editProfileDialog.show(ft, "dialog");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        initUI(sharedPreferences);
    }

    @Override
    public void onStart() {
        super.onStart();
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");
    }
}
