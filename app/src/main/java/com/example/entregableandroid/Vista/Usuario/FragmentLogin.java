package com.example.entregableandroid.Vista.Usuario;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.entregableandroid.R;
import com.example.entregableandroid.databinding.FragmentLoginBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLogin extends Fragment {

    private final static int ACCION_LOGERAR_GOOGLE = 5;
    private String TAG = getClass().toString();
    private static final int RC_SIGN_IN = 78;
    private static final int RC_SIGN_IN2 = 79;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FragmentLoginBinding binding;
    private CallbackManager callbackManager;

    public FragmentLogin() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        escucharBotonGoogle();

        callbackManager = CallbackManager.Factory.create();

        escucharBotonFacebook();

        escucharBotonLoginMail();
        escucharBotonRegistrarse();
        escucharBotonRecuperarPassword();

        return binding.getRoot();
    }


    private void escucharBotonRegistrarse() {
        binding.botonRegisrtrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = binding.usarioMail.getText().toString();
                if (mail.length() < 6) {
                    Snackbar.make(binding.getRoot(), "Mail muy corto", Snackbar.LENGTH_LONG).show();
                    return;
                }
                String password = binding.usuarioPassword.getText().toString();
                if (password.length() < 6) {
                    Snackbar.make(binding.getRoot(), "Password muy corto", Snackbar.LENGTH_LONG).show();
                    return;
                }
                crearUsuario(mail, password);
            }

        });
    }

    private void crearUsuario(String mail, String password) {
        mAuth.createUserWithEmailAndPassword(mail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            enviarMailVerificacion();
                            Log.d(TAG, "Creamos un usuario nuevo");
                        } else {
                            String mensajeError = Objects.requireNonNull(task.getException()).getMessage();
                            Snackbar.make(binding.getRoot(), "Error:" + mensajeError, Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void enviarMailVerificacion() {
        mAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Snackbar.make(binding.getRoot(), "Mail de verificacion enviado, verifique su email por favor", Snackbar.LENGTH_LONG).show();
                        Log.d(TAG, "Verifique que llego el mail a su casilla por favor");
                    }
                });
    }

    private void escucharBotonLoginMail() {
        binding.botonLoginMailyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = binding.usarioMail.getText().toString();
                if (mail.length() < 6) {
                    Snackbar.make(binding.getRoot(), "Mail muy corto", Snackbar.LENGTH_LONG).show();
                    return;
                }
                String password = binding.usuarioPassword.getText().toString();
                if (password.length() < 6) {
                    Snackbar.make(binding.getRoot(), "Password muy corto", Snackbar.LENGTH_LONG).show();
                    return;
                }
                logearConMailyPassword(mail, password);
            }
        });
    }

    private void logearConMailyPassword(String mail, String password) {
        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {
                                Snackbar.make(binding.getRoot(), "Bienvenido!!!", Snackbar.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().popBackStack();
                            } else {
                                mAuth.signOut();
                                Snackbar.make(binding.getRoot(), "Es necesario validar el mail", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(binding.getRoot(), "Usuario o contraseña erroneo", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void escucharBotonRecuperarPassword() {
        binding.botonRecuperarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = binding.usarioMail.getText().toString();
                if (mail.length() < 5) {
                    Snackbar.make(binding.getRoot(), "Mail muy corto", Snackbar.LENGTH_LONG).show();
                    return;
                }
                enviarMaildeRecuperacion(mail);
            }
        });
    }

    private void enviarMaildeRecuperacion(String mail){
        mAuth.sendPasswordResetEmail(mail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(binding.getRoot(), "Mail de recuperacion enviado", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void escucharBotonFacebook() {
        binding.buttonLogearFacebook.setReadPermissions("email", "public_profile");
        binding.buttonLogearFacebook.setFragment(this);
        binding.buttonLogearFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "Se conecto con Facebook, intenteremos con Firebase");
                firebaseAuthWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, " onCancel Facebook");
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG, " onError Facebook");
                // App code
            }
        });
    }

    private void firebaseAuthWithFacebook(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), listenerLogin);
    }


    private void escucharBotonGoogle() {
        binding.botonLogearGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logearGoogle();
            }
        });
    }

    public void logearGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                Log.d(TAG, "Se conecto con google, vamos a intentar con Firebase");
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), listenerLogin);
    }

    OnCompleteListener<AuthResult> listenerLogin = new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "Nos logeamos en Firebase");
                    Snackbar.make(binding.getRoot(), "Nos conectamos!!!", BaseTransientBottomBar.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Log.d(TAG, "Error insperado en Firebase");
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.getException());
                Toast.makeText(getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding = null;
        }
    }

}
