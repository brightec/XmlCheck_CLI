# XmlCheck_CLI

This CLI tool is designed to help Android developer produce the best XML layouts they can. This CLI tool runs a variety of checks against the given files to ensure certain styles are adhered to.

## Usage

Add the jar to your project found on the releases page.

You must specifiy the path you wish to run the checks on:

`xmlcheck ./src/main/res/layout`

## Options

- `-x` `--exclude` - The rules you want to exclude (e.g. `xmlcheck ./src/main/res/layout -x Rule1,Rule2`).
- `--fail-on-empty` or `--no-fail-on-empty` - Whether or not you want the checks to fail if you provide and empty or non-existent path (e.g. `xmlcheck ./src/main/res/layout --fail-on-empty`). Defaults to true.

## Rules

### Element

#### ClassMaterialButton

Ensure that all buttons are `MaterialButton`'s rather than normal `Button`'s. This is imnportant if you are implementing a MaterialComponents theme.

### Attribute

#### TextSizeUnit

`android:textSize` should be specified in sp

#### IdPlus

`android:id` should start with `@+id/`

#### IdNaming

`android:id` should adhere to naming conventions

Generally you should start your id with the first word of the class name e.g. `image_something`

#### Margin2s

All margins should follow the 2's rule i.e. 2, 4, 6, 8, 12, 16, 24, 32, 64

#### ConstraintIdPlus

All references to id's should start with `@+id/`

#### ColorRes

All colors should be specifed in resources rather than hex

## License

See [license](LICENSE)

## Author

Alistair Sykes - [Github](https://github.com/alistairsykes) [Medium](https://medium.com/@alistairsykes) [Twitter](https://twitter.com/SykesAlistair)

This tool is maintained by the [Brightec](https://www.brightec.co.uk/) team
