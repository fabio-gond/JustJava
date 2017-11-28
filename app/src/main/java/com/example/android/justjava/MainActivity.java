package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int qtaCoffees=0;
    int singlePrice=5;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;
    int duration = Toast.LENGTH_SHORT;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //displayPrice(calculatePrice());
        String mail= createOrderSubmary(calculatePrice());
        composeEmail("Just Java order for Kaptain Kunal",mail);
    }
    public void increment(View view){
        if(qtaCoffees>99){
            Toast.makeText(context, "You can't add more then 100 coffes!", duration).show();
            return;
        }
        qtaCoffees++;
        displayQuantity(qtaCoffees);
    }
    public void decrement(View view){
        if(qtaCoffees<1){
            Toast toast= Toast.makeText(context, "You can't choose a negative number of coffes!", duration);
            toast.setGravity(Gravity.BOTTOM,0,30);
            toast.show();
            return;
        }
        qtaCoffees--;
        displayQuantity(qtaCoffees);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice() {
        CheckBox checkWhipped = (CheckBox) findViewById(R.id.check_whipped);
        hasWhippedCream= checkWhipped.isChecked();
        CheckBox checkChocolate = (CheckBox) findViewById(R.id.check_chocolate);
        hasChocolate= checkChocolate.isChecked();
        int toppings_price= (hasWhippedCream)? 1 : 0;
        toppings_price+= (hasChocolate)? 2 : 0;
        int price = qtaCoffees * singlePrice + toppings_price;
        return price;
    }
    private String createOrderSubmary(int price){
        EditText inpName = (EditText) findViewById(R.id.inp_name);
        String name = inpName.getText().toString();
        String text=getString(R.string.msg_name)+name+"\nAdd Whipped Cream? "+String.valueOf(hasWhippedCream)+"\nAdd Chocolate? "+String.valueOf(hasChocolate)+"\nQuantity: " + qtaCoffees + "\n Total: $" +price +"\n Thank You!";
        return text;
    }
    private void composeEmail(String subject,String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
