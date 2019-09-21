package samidsoft.co.passwordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import samidsoft.co.passwordgenerator.database.NoteRepository;
import samidsoft.co.passwordgenerator.model.Note;
import samidsoft.co.passwordgenerator.utils.PasswordGenerator;

public class MainActivity extends AppCompatActivity {
    private String GENERATED_PASSWORD_TAG = "GENERATED_PASSWORD";
    private String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.password_result)
    AppCompatTextView passwordResult;

    @BindView(R.id.password_length_hint)
    AppCompatTextView lengthTitle;

    @BindView(R.id.password_length_seek_bar)
    DiscreteSeekBar passwordLengthSeekBar;

    @BindView(R.id.lower_letters_switch)
    SwitchCompat lowerLettersSwitch;

    @BindView(R.id.upper_letters_switch)
    SwitchCompat upperLettersSwitch;

    @BindView(R.id.digits_switch)
    SwitchCompat digitsSwitch;

    @BindView(R.id.symbols_switch)
    SwitchCompat symbolsSwitch;

    @BindView(R.id.btn_share)
    AppCompatImageButton share;

    @BindView(R.id.btn_copy)
    AppCompatImageButton copy;

    @BindView(R.id.img_add)
    AppCompatImageButton img_add;


    @OnClick(R.id.lower_letters_container)
    void toggleLowerLetters() {
        lowerLettersSwitch.setChecked(!lowerLettersSwitch.isChecked());
    }

    @OnClick(R.id.upper_letters_container)
    void toggleUpperLetters() {
        upperLettersSwitch.setChecked(!upperLettersSwitch.isChecked());
    }

    @OnClick(R.id.digits_container)
    void toggleDigits() {
        digitsSwitch.setChecked(!digitsSwitch.isChecked());
    }

    @OnClick(R.id.symbols_container)
    void toggleSymbols() {
        symbolsSwitch.setChecked(!symbolsSwitch.isChecked());
    }

    @OnClick(R.id.generate_password_button)
    void generatePassword() {
        try {
            passwordResult.setText(getPasswordGenerator().generate(passwordLengthSeekBar.getProgress()));
        } catch (Exception e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    @OnClick(R.id.btn_copy)
    void copyPassword() {
        String password = passwordResult.getText().toString();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(password, password);

        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, getResources().getString(R.string.password_copied), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.btn_share)
    void sharePassword() {
        ShareCompat.IntentBuilder
                .from(this)
                .setType("text/plain")
                .setText(passwordResult.getText().toString())
                .startChooser();
    }

    @OnClick(R.id.img_add)
    void addNotes() {
        startActivity(new Intent(this, NotesActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        lengthTitle.setText(String.format(getResources().getString(R.string.length),
                String.valueOf(passwordLengthSeekBar.getProgress())));

        passwordLengthSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                lengthTitle.setText(String.format(getResources().getString(R.string.length), String.valueOf(value)));
                //lengthTitle.setText(String.format(getResources().getString(R.string.length), String.valueOf(value)));
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        // Generate password automatically when app starts
        if (TextUtils.isEmpty(passwordResult.getText()))
            generatePassword();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GENERATED_PASSWORD_TAG, passwordResult.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        passwordResult.setText(savedInstanceState.getString(GENERATED_PASSWORD_TAG));
    }

    private PasswordGenerator getPasswordGenerator() {
        return new PasswordGenerator.Builder()
                .useLower(lowerLettersSwitch.isChecked())
                .useUpper(upperLettersSwitch.isChecked())
                .useDigits(digitsSwitch.isChecked())
                .usePunctuation(symbolsSwitch.isChecked()).build();
    }


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add) {
            Toast.makeText(this, "add", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }*/
}
