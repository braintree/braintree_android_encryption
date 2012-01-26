# Braintree Android Encryption

This library is for use with [Braintree's payment gateway](http://braintreepayments.com/) in concert with one of [the supported client libraries](http://braintreepayments.com/docs).  It encrypts sensitive payment information using the public key of an asymmetric key pair.

## Getting Started

There are a couple of ways to use the Android client encryption library.

### Jar File

1. Simply download the jar file from this repo and add it to your Android project.

### Android Library Project (SDK r6 or higher)

_Note: This assumes you are using Eclipse as your development IDE._

1. Clone this repo.
2. Import the Braintree Android library project into your Eclipse workspace.
3. Go to your project's properties.
4. Under Android, Library section, add the Braintree Android library project.

Here's a quick example.

Configure the library to use your public key.

```java
Braintree braintree = new Braintree("YOUR_CLIENT_SIDE_PUBLIC_ENCRYPTION_KEY");
```

And call the `encrypt` method passing in the data you wish to be encrypted.

```java
String encryptedValue = braintree.encrypt("sensitiveValue");
```

Because we are using asymmetric encryption, you will be unable to decrypt the data you have encrypted using your public encryption key. Only the Braintree Gateway will be able to decrypt these encrypted values.  This means that `encryptedValue` is now safe to pass through your servers to be used in the Server-to-Server API of one of our client libraries.

## Retrieving your Encryption Key

When Client-Side encryption is enabled for your Braintree Gateway account, a key pair is generated and you are given a specially formatted version of the public key.

## Encrypting Form Values

The normal use case for this library is to encrypt a credit card number and CVV code before a form is submitted to your servers.  A simple example of this in Android might look something like this:

```java
public class BraintreeActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void submitForm(View view) {
        String ccNumber = encryptFormField(creditCard);
        String ccExpDate = encryptFormField(expirationDate);
        String ccCvv = encryptFormField(cvv);

        postTask = new PostToMerchantServerTask();
        postTask.execute(new String[] { ccNumber, ccExpDate, ccCvv });
    }

    private String getFieldText(EditText field) {
        return new String(field.getText().toString());
    }

    private String encryptFormField(View formField) {
        String formFieldText = getFieldText((EditText) formField);
        Braintree braintree = new Braintree(publicKey);
        return braintree.encrypt(formFieldText);
    }
}
```
