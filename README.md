# TWL

## What is TWL

TWL is a graphical user interface library for Java built on top of OpenGL. It provides a rich set of standard widgets including labels, edit fields, tables, popups, tooltips, frames and a lot more. Different layout container are available to create even the most advanced user interfaces.

With the TextArea class TWL also features a powerful HTML renderer which supports a subset of XHTML/CSS: floating elements, tables, images, unordered lists, text alignment, justified text and background images. See the TextArea Demo for details. It is perfectly suited to create NPC chat dialogs in games or integrated help systems.

As games have a high demand on visual identity, TWL provides a very flexible theme manager. The theme manager decouples the visual representation of widgets from the code. Themes are specified in XML and PNG files with full alpha blending for effects such as glow or shadows. These themed can be created using the TWL Theme Editor, which also includes a powerful tool to create bitmap fonts from TrueType fonts.

## TODO

[x] Allow use with LWJGL 3

[ ] Fix all examples - (mostly done just a few left)

[ ] Fix the test that broke when formatting and removing unneeded imports

[ ] create renderer that uses 3.2+ OpenGL

[ ] recode 90% of what was done to be better

## Bugs

Some tests were messed up

## Disclaimer

TWL was not created by me, ShadowLordAlpha, it was created by Matthias Mann and other contributers. This is just my attempt at updating TWL. 

Currently it works by simply taking control of the callbacks it needs and emulating the old LWJGL 2 way of doing input and then passing the callback data onto the old callback. This is not perfect and will break if the user attempts to rebind or remove a callback that is set by TWL. The whole system needs to be updated but it works this way for now.
