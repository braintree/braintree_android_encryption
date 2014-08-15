# Braintree Android Encryption

This library is for use with [Braintree's payment gateway](http://braintreepayments.com/) in concert
with one of [the supported client libraries](http://braintreepayments.com/docs).  It encrypts
sensitive payment information using the public key of an asymmetric key pair.

## Install

### Gradle

In your `build.gradle`, add the following:

```groovy
dependencies {
  ...
  compile 'com.braintreepayments:encryption:2.+'
}
```

### Maven

In your `pom.xml` add the following:

```xml
<dependencies>
  ...
    <dependency>
      <groupId>com.braintreepayments</groupId>
      <artifactId>encryption</artifactId>
      <version>[2.0,)</version>
      <type>jar</type>
    </dependency>
  </dependencies>
```

### Jar File

Download the [jar file](https://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=com.braintreepayments&a=encryption&v=LATEST)
and include it in your project.

## Usage

Configure the library to use your public key.

```java
Braintree braintree = new Braintree("YOUR_CLIENT_SIDE_PUBLIC_ENCRYPTION_KEY");
```

And call the `encrypt` method passing in the data you wish to be encrypted.

```java
String encryptedValue = braintree.encrypt("sensitiveValue");
```

Because we are using asymmetric encryption, you will be unable to decrypt the data you have
encrypted using your public encryption key. Only the Braintree Gateway will be able to decrypt
these encrypted values.  This means that `encryptedValue` is now safe to pass through your servers
to be used in the Server-to-Server API of one of our client libraries.

## Retrieving your Encryption Key

When Client-Side encryption is enabled for your Braintree Gateway account, a key pair is generated
and you are given a specially formatted version of the public key.

## Encrypting Form Values

The normal use case for this library is to encrypt a credit card number and CVV code before a form
is submitted to your servers.  A simple example of this in Android might look something like this:

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

## License

braintree_android_encryption is open source and available under the MIT license. See the
[LICENSE](LICENSE) file for more info.
