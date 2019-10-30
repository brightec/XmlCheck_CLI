<div align="center">
  <img width="100" src="https://github.com/brightec/XmlCheck_CLI/blob/master/Logo.png">
</div>

# XmlCheck_CLI

This CLI tool is designed to help Android developers produce the best XML layouts they can. This tool runs a variety of rules against the given files to ensure certain styles are adhered to. These rules are styles we adhere to within our team. Yours may (and probably will) be different, so why not fork this project and add your own. Our intention is not to impose a style guide, but to offer a tool to help you stick to yours.

## Table of Contents

- [Usage](#usage)
- [Options](#options)
- [Our Rules](#our-rules)
  + [Element](#element)
    - [ClassMaterialButton](#classmaterialbutton)
  + [Attribute](#attribute)
    - [TextSizeUnit](#textsizeunit)
    - [IdPlus](#idplus)
    - [IdNaming](#idnaming)
    - [Height2s](#height2s)
    - [Width2s](#width2s)
    - [Margin2s](#margin2s)
    - [ConstraintIdPlus](#constraintidplus)
    - [ColorRes](#colorres)
- [Suppression](#suppression)
- [Customise](#customise)
  + [Types of rule](#types-of-rule)
  + [Adding a rule](#adding-a-rule)
    - [1. Add the Rule class](#1-add-the-rule-class)
    - [2. Implement the rule](#2-implement-the-rule)
    - [3. Add the rule to the checker](#3-add-the-rule-to-the-checker)
    - [4. Testing the rule](#4-testing-the-rule)
    - [5. Running and building](#5-running-and-building)
- [Acknowledgments](#acknowledgments)
- [License](#license)
- [Author](#author)

## Usage

Add the jar to your project found on the releases page.

You must specify the path you wish to run the rules on:

`xmlcheck ./src/main/res/layout`

## Options

- `-h` `--help` - Show help (e.g. `xmlcheck --help`).
- `-x` `--exclude` - The rules you want to exclude (e.g. `xmlcheck ./src/main/res/layout --exclude Rule1,Rule2`).
- `--fail-on-empty` or `--no-fail-on-empty` - Whether or not you want the checks to fail if you provide an empty or non-existent path (e.g. `xmlcheck ./src/main/res/layout --fail-on-empty`). Defaults to failing on empty.

## Our Rules

### Element

#### ClassMaterialButton

Ensure that all buttons are `MaterialButton`'s rather than normal `Button`'s. This is important if you are implementing a MaterialComponents theme.

### Attribute

#### TextSizeUnit

`android:textSize` should be specified in sp.

#### IdPlus

`android:id` should start with `@+id/`.

#### IdNaming

`android:id` should adhere to our naming conventions.

Generally we should start our id's with the first word of the class name e.g. `image_something`. There are some specific exceptions though which can be found within the rule: `src/main/kotlin/uk/co/brightec/xmlcheck/rules/attr/android/Id.kt`.

#### Height2s

All heights should follow the 2's rule i.e. 2, 4, 6, 8, 12, 16, 24, 32, 64. Or be wrap_content, match_parent, etc.

#### Width2s

All widths should follow the 2's rule i.e. 2, 4, 6, 8, 12, 16, 24, 32, 64. Or be wrap_content, match_parent, etc.

#### Margin2s

All margins should follow the 2's rule i.e. 2, 4, 6, 8, 12, 16, 24, 32, 64.

#### ConstraintIdPlus

All references to id's should start with `@+id/`.

#### ColorRes

All colors should be specified in resources rather than hex.

## Suppression

In addition to being able to exclude a rule, you can also suppress specific instances by adding `tools:ignore="Rule1,Rule2"`.

## Customise

To add your own rules, fork this project and add away. Why not share them with us too, by submitting an issue.

### Types of rule

We have two types of rules, attribute and element. Attribute rules define a rule for an XML attribute and Element for the entire XML element.

### Adding a rule

Let's say we wanted to add an attribute rule for the `android:text` attribute. We want all our text to always contain `I Love XML`.

#### 1. Add the Rule class

First, we will add the class for a new `AttrRule` for the `android:text` attribute.

`src/main/kotlin/uk/co/brightec/xmlcheck/rules/attr/android/TextLoveXml.kt`
```
package uk.co.brightec.xmlcheck.rules.attr.android

import org.w3c.dom.Attr
import uk.co.brightec.xmlcheck.Constants.ATTR_NAMESPACE_ANDROID
import uk.co.brightec.xmlcheck.Failure
import uk.co.brightec.xmlcheck.rules.attr.AttrRule

class TextLoveXml : AttrRule(
    name = "TextLoveXml",
    attrName = "$ATTR_NAMESPACE_ANDROID:text"
) {

    override fun run(node: Attr): Failure<Attr>? {
        // ...
    }
}
```

We subclass `AttrRule` and then override the required methods.
- `name` - The name of your rule
- `attrName` or `attrNames` - The attribute(s) this rule should apply to i.e. `android:text`
- `run()` - This is where we will actually implement our rule logic

#### 2. Implement the rule

To implement our rule we adjust the `run()` implementation.

```
override fun run(node: Attr): Failure<Attr>? {
    return if (node.value.contains("I Love XML")) {
        null
    } else {
        failure(node, "All text should love XML")
    }
}
```

We need to check whether this attribute has violated the rule. If it has, we need to return a `Failure`, if not then return `null`.

#### 3. Add the rule to the checker

Within `uk.co.brightec.xmlcheck.Checker.kt`, we add our new attribute rule to the `allAttrRules` list.

```
private val allAttrRules: List<AttrRule> = arrayListOf(
    // ...
    TextLoveXml()
)
```

Now XMLCheck is aware of our rule, and we can run XMLCheck and our new rule will be checked against.

#### 4. Testing the rule

We have two kinds of tests within the project. Unit and EndToEnd. Within Unit tests we mock an attribute and then assert various cases about our rule. Within EndToEnd, we run our rules against an XML file and verify the expected output (i.e. command line output).

Checkout some of the tests we have already written for an example of how to tests for your new rules.

#### 5. Running and building

There are a few gradle tasks to aid with development and distribution
- `installAndRun` - This installs the current state of the project and runs it against the XML files within `src/test/resources/files/cases/`
- `installAndHelp` - This installs the current state of the project and runs the help command
- `check` - This runs all the tests and checks against the project
- `distJar` - Creates a jar file for distribution which can be found within `build/distributions/`
- `distTar` - Creates a tar file for distribution which can be found within `build/distributions/`
- `distZip` - Creates a zip file for distribution which can be found within `build/distributions/`

## Acknowledgments

- [Kotlin](https://kotlinlang.org/)
- [Clikt](https://ajalt.github.io/clikt/) - Clikt (pronounced “clicked”) is a Kotlin library that makes writing command line interfaces simple and intuitive. It is the “Command Line Interface for Kotlin”. I have really enjoyed using this library, it makes writing command line interfaces very simple.
- [JUnit5](https://junit.org/junit5/) - JUnit5 has helped make the testing simple and easier (especially having parameterized tests).
- [Mockk](https://mockk.io/) - Mockk is our mocking framework of choice for Kotlin.

## License

See [license](LICENSE)

## Author

Alistair Sykes - [Github](https://github.com/alistairsykes) [Medium](https://medium.com/@alistairsykes) [Twitter](https://twitter.com/SykesAlistair)

This tool is maintained by the [Brightec](https://www.brightec.co.uk/) team
