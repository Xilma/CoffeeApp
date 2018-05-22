package com.example.android.coffeeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if(quantity >= 100){
            Toast.makeText(MainActivity.this, "You cannot have more than 100 cups of coffee",
                    Toast.LENGTH_LONG).show();
        }
        else{
            quantity = quantity + 1;
        }
        displayQuantity(quantity);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity <= 0){
            Toast.makeText(MainActivity.this, "You cannot have less than 1 cup of coffee",
                    Toast.LENGTH_LONG).show();
        }
        else{
            quantity = quantity - 1;
        }
        displayQuantity(quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText name = (EditText) findViewById(R.id.name);
        String nameValue = name.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + nameValue);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    /**
     * Calculates the price of the order.
     */

    private int calculatePrice() {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        int basePrice=5;
        if(whippedCream.isChecked() == true){
            basePrice += 1;
        }
        else if(chocolate.isChecked() == true){
            basePrice += 2;
        }
        else {
            basePrice += 0;
        }
        return quantity * basePrice;
    }
    /**
     * Creates an order summary
     *
     */
    private String createOrderSummary(){
        EditText name = (EditText) findViewById(R.id.name);
        String nameValue = name.getText().toString();

        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);

        boolean hasChocolate = chocolate.isChecked();
        boolean hasWhippedCream = whippedCream.isChecked();

        String summary = "Name: " + nameValue +
                "\nAdd whipped cream? " + hasWhippedCream +
                "\nAdd chocolate? " + hasChocolate +
                "\nQuantity: " + quantity +
                "\nTotal: " + calculatePrice() +
                "\nThank you!";
        return summary;
    }
}