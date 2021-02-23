English | [简体中文](./README_CN.md)

# FormatEdit

An easy to use format edit library for Android. 

[![Download](https://api.bintray.com/packages/taylorzhang/maven/format-edit/images/download.svg?style=flat)](https://bintray.com/taylorzhang/maven/format-edit/)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![License](https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg?style=flat)](LICENSE)
[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

## Download

```groovy
dependencies {
    implementation 'cc.taylorzhang:format-edit:1.1.0'
}
```

## Usage

### Format edit phone

layout：

```xml
<androidx.appcompat.widget.AppCompatEditText
    android:id="@+id/etPhone"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:inputType="number" />
```

kotlin:

```kotlin
// format is ' '
etPhone.setFormatRules(3, 4, 4)

// format is '-'
etPhone.setFormatRules(3, 4, 4, formatChar = '-')
```

![](screenshots/format_edit_phone.gif)

### Format edit ID number

layout:

```xml
<androidx.appcompat.widget.AppCompatEditText
    android:id="@+id/etIDNumber"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:digits="@string/digits_id_number"/>
```

resources:

```xml
<string name="digits_id_number">0123456789xX</string>
```

kotlin:

```kotlin
etIDNumber.setFormatRules(6, 4, 4, 4)
```

![](screenshots/format_edit_id_number.gif)

### Set listener

```kotlin
etPhone.setOnFormatEditListener { isComplete, text ->
    if (isComplete) { // edit complete
        // toast current text with format removed.
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
```

### Text with format removed

```kotlin
etPhone.textWithFormatRemoved
```

## License

[Apache license 2.0](LICENSE) © Taylor Zhang