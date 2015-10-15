package com.example.javafx.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import com.sun.javafx.css.converters.ColorConverter;

import javafx.css.CssMetaData;
import javafx.css.SimpleStyleableObjectProperty;
import javafx.css.Styleable;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * A seven segment display panel which is capable of displaying fixed point
 * numbers and some limited text.
 */
public class SevenSegmentPanel extends Control {

    private SevenSegment[] digits;
    private String format;

    /**
     * Creates a new seven segment display panel.
     * Initially, all segments of the display are turned off.
     * Then, the display contents can be set with the setText() and setValue() 
     * methods.
     *
     * @param numberOfDigits The number of digits for the panel.
     */
    public SevenSegmentPanel(int numberOfDigits) {
        this(numberOfDigits, 0);
    }


    /**
     * Creates a new seven segment display panel.
     * Initially, all segments of the display are turned off.
     * Then, the display contents can be set with the setText() and setValue() 
     * methods.
     *
     * @param numberOfDigits The overall number of digits
     * @param decimalCount The number of decimal places
     */
    public SevenSegmentPanel(int numberOfDigits, int decimalCount) {
        this.getStyleClass().add("seven-segment-panel");

        final HBox displayGroup = new HBox();
        displayGroup.setId("segments");

        digits = new SevenSegment[numberOfDigits];
        for (int i = 0;  i < numberOfDigits;  i++) {
            digits[i] = new SevenSegment();
            digits[i].setId("seg" + i);
            displayGroup.getChildren().add(digits[i]);
        }
        getChildren().add(displayGroup);
        displayGroup.setMaxSize(displayGroup.getMaxWidth(), displayGroup.getPrefHeight());

        
        
        // TODO: why is this done in onColorProperty() originally??
        onColor = new SimpleStyleableObjectProperty<Color>(
                        StyleableProperties.ON_COLOR, this, "onColor", Color.YELLOW);
                        //new Color(0x23 / 255.0, 0x2A / 255.0, 0x47 / 255.0, 1.0));
        onColor.addListener( (obs, oldVal, newVal) -> {
            for (SevenSegment s : digits) {
                s.setOnColor(newVal);
            }
        });
        setOnColor(onColor.get());

        offColor = new SimpleStyleableObjectProperty<Color>(
                StyleableProperties.OFF_COLOR, this, "offColor", 
                new Color(0xC9 / 255.0, 0xD3 / 255.0, 0xBB / 255.0, 1.0));
        offColor.addListener( (obs, oldVal, newVal) -> {
            for (SevenSegment s : digits) {
                s.setOffColor(newVal);
            }
        });
        setOffColor(offColor.get());

/*        this.backgroundProperty().addListener((obs, oldVal, newVal) -> {
            Color newPaint = (Color) newVal.getFills().get(0).getFill();
            
            for (SevenSegment s : digits) {
                s.setBackgroundColor(newPaint);
            }
        });
*/

        // create the format specifier for floating point conversion
        format = String.format("%%%d.%df", numberOfDigits + 1, decimalCount);
    }


    /**
     * Sets a floting point number to display in the display panel.
     * The number is auto-formatted to fit the setup of the display regarding
     * the number of digits and the number of decimal places.
     *
     * @param value The floating point number to display.
     */
    public void setValue(double value) {
        String text = String.format(Locale.US, format, value);    // Important: use dot as decimal point!
        setText(text);
/*
        int whole = (int) value;
        int frac = (int) (value * factor);

        // TODO: probably it is better to format the floating point number 
        // into a String and the use the setText method.
 
        int decimalPointIdx = digits.length - decimalCount - 1;
        int i = 0;
        for (i = digits.length - 1;  i > decimalPointIdx;  i--) {
           int dig = frac % 10;
           frac = frac / 10;
           digits[i].setDigit(dig);
           digits[i].setDP(false);
        }
        for (   ;  i >= 0;  i--) {
           int dig = whole % 10;
           whole = whole / 10;
           digits[i].setDigit(dig);
           digits[i].setDP(false);
           if (whole == 0) {
               i--;
               break;
           }
        }
        for (   ;  i >= 0;  i--) {
            digits[i].clear();
            digits[i].setDP(false);
        }
        digits[decimalPointIdx].setDP(true);
*/
    }


    /**
     * Convenience method to display text in the display panel.
     * Supported characters are:  
     * 
     * 0123456789abcdefHlLoOPrv�. and the space character.
     *
     * @param text
     */
    public void setText(String text) {
        // TODO: use Java 1.8 chars() method!

        int didx = 0;
        for (int i = 0;  i < text.length() && didx < digits.length; i++) {
            char c = text.charAt(i);
            if (c == '.') {
                digits[didx-1].setDP(true); // DP is located in previous digit!
            } else {
                digits[didx].setChar(c);
                digits[didx].setDP(false);
                didx++;
            }
        }
    }

    

/** Skinning and style sheet support */

    @Override
    protected javafx.scene.control.Skin<?> createDefaultSkin() {
        return new SevenSegmentPanelSkin(this);
    };

    // This private static class defines all CSS properties which are supported by this control
    private static class StyleableProperties {

        private static final CssMetaData<SevenSegmentPanel, Color> ON_COLOR = // TODO: HOLD ON - xxxConverter classes are part of com.sun!!!!
                new CssMetaData<SevenSegmentPanel, Color>("-fx-on-color", ColorConverter.getInstance(), Color.ORANGE) {

                    @Override
                    public boolean isSettable(SevenSegmentPanel styleable) {
                        boolean result = styleable.onColor == null || !styleable.onColor.isBound();
                        return result;
                    }

                    @Override
                    public StyleableProperty<Color> getStyleableProperty(
                            SevenSegmentPanel styleable) {
                        return styleable.onColorProperty();
                    }
        };

        private static final CssMetaData<SevenSegmentPanel, Color> OFF_COLOR = // TODO: HOLD ON - xxxConverter classes are part of com.sun!!!!
                new CssMetaData<SevenSegmentPanel, Color>("-fx-off-color", ColorConverter.getInstance(), Color.BLUE) {

                    @Override
                    public boolean isSettable(SevenSegmentPanel styleable) {
                        boolean result = styleable.offColor == null || !styleable.onColor.isBound();
                        return result;
                    }

                    @Override
                    public StyleableProperty<Color> getStyleableProperty(
                            SevenSegmentPanel styleable) {
                        return styleable.offColorProperty();
                    }
        };

        private static final List<CssMetaData<? extends Styleable, ?>> PROPERTIES;

        static {
            // get list of default CSS properties from this control's parent class (Control)
            final List<CssMetaData<? extends Styleable, ?>> styleables = 
                    new ArrayList<>(Control.getClassCssMetaData());

            // add the additional properties from this class
            Collections.addAll(styleables, ON_COLOR, OFF_COLOR);

            PROPERTIES = Collections.unmodifiableList(styleables);
        }
        
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        List<CssMetaData<? extends Styleable, ?>> result = StyleableProperties.PROPERTIES;
        return result;
    }
    
   
    // TODO: change listener!
    // private ObjectProperty<Color> onColor = new SimpleObjectProperty<>(new Color(0x23 / 255.0, 0x2A / 255.0, 0x47 / 255.0, 1.0));
    private StyleableObjectProperty<Color> onColor = null;

    // getter
    public Color getOnColor() {
        return onColor.get();
    }

    /**
     * Sets the color to use for enabled segments.
     *
     * @param color The color to use when painting enabled segments.
     */
    public void setOnColor(Color col) {
        onColor.set(col);
    }

    // property access
    public StyleableObjectProperty<Color> onColorProperty() {
        return onColor;
    }

    // TODO: change listener!
    //private Color offColor = new Color(0xC9 / 255.0, 0xD3 / 255.0, 0xBB / 255.0, 1.0);
    private StyleableObjectProperty<Color> offColor = null;

    // getter
    public Color getOffColor() {
        return offColor.get();
    }

    /**
     * Sets the color to use for disabled segments.
     *
     * @param color The color to use when painting disabled segments.
     */
    public void setOffColor(Color col) {
        offColor.set(col);
    }

    // property access
    public StyleableObjectProperty<Color> offColorProperty() {
        return offColor;
    }

}