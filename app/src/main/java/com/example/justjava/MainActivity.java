package com.example.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int quantity =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Find users name.
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        // Finds if user needs Whipped Cream.
        CheckBox addWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = addWhippedCream.isChecked();

        // Finds if user wants Chocolate or not.
        CheckBox addChocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = addChocolate.isChecked();

        // Calculates the Price.
        int price=calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage=createOrderSummary(price,hasWhippedCream,hasChocolate,name);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        boolean addresses=true;
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order For " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     *
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name)
    {
        String priceMessage=getString(R.string.order_summary_name, name);
        priceMessage += "\n" +"Whipped Cream Added : " + hasWhippedCream;
        priceMessage += "\n" + "Chocolate Added : " + hasChocolate;
        priceMessage+= "\n" + getString(R.string.quantity_of_coffee, quantity);
        priceMessage+= "\n" + getString(R.string.total) + price + getString(R.string.Rupee) + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given text on the screen.
     */

    /**
     * This method is called when + button is clicked.
     */
    public void increment(View view){
        if(quantity == 100)
        {
            // Shows an error message.
            Toast.makeText(this, "You cannot have more than 100 Coffee at a time.", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when '-' is clicked.
     */
    public void decrement(View view){
        if(quantity == 1)
        {
            // Shows ans Error message.
            Toast.makeText(this,"You cannot have less than 1 Coffee" , Toast.LENGTH_LONG).show();
            return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate)
    {
        int basePrice = 5;

        if(addWhippedCream)
        {
            basePrice+=1;
        }
        if(addChocolate)
        {
            basePrice+=2;
        }
        return quantity * basePrice;
    }
}