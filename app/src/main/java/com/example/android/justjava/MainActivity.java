/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.graphics.Color;
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

    /**
     * Here @extends is helping MainActivity inheriting all the behaviours from AppCompatActivity super class
     * Where all the methods are defined such as setContentView, onCreate, findViewById
     */

    int quantity = 2;

    /**
     * Here @Override means that we are changing (over riding) some of the method functionality
     * which we are inheriting from AppCompatActivity super class
     * We are changing onCreate method to have our xml layout in the app
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Here is this method we don't let the user input more than 100 times of coffees
     * So giving a condition which only works if the input reaches 100, immediately stops any function
     * Otherwise continues its normal function
     *
     * @param view
     */

    public void increment(View view) {
        if (quantity == 100) {
            Toast toast = Toast.makeText(MainActivity.this, "You can not have more than 100 coffees", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * Here is this method we don't let the user input less than 1 times of coffees
     * So giving a condition which only works if the input reaches 1, immediately stops any function
     * Otherwise continues its normal function
     *
     * @param view
     */

    public void decrement(View view) {
        if (quantity == 1) {
            display(quantity);
            Toast toast = Toast.makeText(MainActivity.this, "You can not have less than 1 coffee", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * Declaring and defining methods for checkBoxes as we want something to happen when user clicks (onClick) the checkboxes
     *
     * @param view
     */

    public void checkBox(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox1); // Finding the checkbox by its id in xml layout and checking if it's checked
        if (checkBox.isChecked()) {
            Toast toast = Toast.makeText(MainActivity.this, "You've added whipped cream", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void checkBox1(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox2);
        if (checkBox.isChecked()) {
            Toast toast = Toast.makeText(MainActivity.this, "You've added chocolate", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void order(View view) {
        EditText editText = (EditText) findViewById(R.id.plain_text_input);
        String name = editText.getText().toString();
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox1);
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkbox2);
        boolean hasChocolate = checkBox1.isChecked();
        boolean hasWhippedCream = checkBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        displayMessage(createOrderSummary(price, name, hasWhippedCream, hasChocolate));
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText editText = (EditText) findViewById(R.id.plain_text_input);
        String name = editText.getText().toString();
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox1);
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkbox2);
        boolean hasChocolate = checkBox1.isChecked();
        boolean hasWhippedCream = checkBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String orderSummary = (createOrderSummary(price, name, hasWhippedCream, hasChocolate));

        // Creating intent to send the order by sms to a specific number
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:+8801799018683"));
        intent.putExtra("sms_body", orderSummary);

        // If condition checks if there is any app to catch this intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary(int price, String name, boolean hasWhippedCream, boolean hasChocolate) {
        return "Name : " + name + "\nAdd Whipped Cream? " + hasWhippedCream + "\nAdd Chocolate? " + hasChocolate + "\nQuantity : " + quantity + "\nTotal : $" + price + "\nThank You!";
    }

    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        if (hasWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }
        return basePrice * quantity;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     * Here TextView is for the TextView in the XML
     * Here TextView is acting as a class and we are making objects of this class which is named orderSummaryTextView
     * And accessing TextView's methods using dots, which is setText, setColor etc
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
        orderSummaryTextView.setTextColor(Color.BLUE);
    }

    /**
     * TextView/ImageView extends from View super class. But TextView/ImageView has some special features that View doesn't have
     * We can also write the above code like this
     * View orderSummaryTextView = findViewById(R.id.order_summary_text_view); findViewById method returning View data type
     * But problem is this is just a normal View data type that does not have special feature of TextView or ImageView
     * So in order to change data type we need to tell explicitly what data type we actually want
     * In this case we want TextView data type
     * So we need to mention this on both side of the expression which is
     * TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
     * Here (TextView) is explicitly ordering to change the data type to TextView from just View. This is called 'casting'
     * and store that in another TextView variable which is orderSummaryTextView
     */
}