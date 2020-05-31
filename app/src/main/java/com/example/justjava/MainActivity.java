package com.example.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This method is called when the plus button is clicked.
     */
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolate = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();
        EditText name_editText = findViewById(R.id.name_edit_textView);
        String name = name_editText.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String price_Message = getString(R.string.price_msg, name);
        price_Message += "\n" + getString(R.string.order_summary_whipped_cream, hasWhippedCream);
        price_Message += "\n" + getString(R.string.order_summary_chocolate, hasChocolate);
        price_Message += "\n" + getString(R.string.order_summary_quantity, quantity);
        price_Message += "\n" + getString(R.string.order_summary_price, "₹" + price);
        price_Message += "\n" + getString(R.string.thank_you);
        return price_Message;
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        display(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }


    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int base_price = 5;
        if (addWhippedCream)
            base_price += 1;
        if (addChocolate)
            base_price += 2;
        return quantity * base_price;
    }
}