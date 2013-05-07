//
// @file   UIUtils.java
// @brief  Utilities for the package org.sbml.testsuite.ui
// @author Michael Hucka
// @date   2013-01-07 <mhucka@caltech.edu>
//
// ----------------------------------------------------------------------------
// This file is part of the SBML Testsuite. Please visit http://sbml.org for
// more information about SBML, and the latest version of the SBML Test Suite.
//
// Copyright (C) 2009-2012 jointly by the following organizations:
// 1. California Institute of Technology, Pasadena, CA, USA
// 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
//
// Copyright (C) 2006-2008 by the California Institute of Technology,
// Pasadena, CA, USA
//
// Copyright (C) 2002-2005 jointly by the following organizations:
// 1. California Institute of Technology, Pasadena, CA, USA
// 2. Japan Science and Technology Agency, Japan
//
// This library is free software; you can redistribute it and/or modify it
// under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation. A copy of the license agreement is provided
// in the file named "LICENSE.txt" included with this software distribution
// and also available online as http://sbml.org/software/libsbml/license.html
// ----------------------------------------------------------------------------

package org.sbml.testsuite.ui;

import java.io.File;
import java.io.InputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.wb.swt.SWTResourceManager;


/**
 * Utility class for org.sbml.testsuite.ui
 */
public class UIUtils
{
    /**
     * Return a resource we have embedded in our application.
     */
    public static Image getImageResource(String fileName)
    {
        return SWTResourceManager.getImage(UIUtils.class, 
            "/org/sbml/testsuite/ui/resources/" + fileName);
    }


    /**
     * Return a resource we have embedded in our application.
     */
    public static InputStream getFileResource(String fileName)
    {
        return UIUtils.class.getResourceAsStream(
            "/org/sbml/testsuite/ui/resources/" + fileName);
    }


    /**
     * Returns true if the key press involved a modifier key.
     * 
     * This handles platform-specific combination, such as using control
     * on Windows vs Command on Mac OS X.
     *
     * @return true if the key had a modifier set, false otherwise.
     */
    public static boolean isModifierKey(KeyEvent e)
    {
        // SWT.MOD1 is supposed to be set to SWT.CONTROL on Windows and to
        // SWT.COMMAND on the Macintosh.
        return (e.stateMask & SWT.MOD1) == SWT.MOD1;
    }


    /**
     * Returns true if the key press involved a shift key.
     *
     * @return true if the key had shift set, false otherwise.
     */
    public static boolean isShiftKey(KeyEvent e)
    {
        return (e.stateMask & SWT.SHIFT) == SWT.SHIFT;
    }


    /**
     * Returns true if the key press involved a modifier key.
     * 
     * This handles platform-specific combination, such as using control on
     * Windows vs Command on Mac OS X.  This is different from
     * isModifierKey(...) because SWT's KeyEvent does not inherit from Event
     * and KeyEvent cannot be cast to Event.
     *
     * @return true if the key had a modifier set, false otherwise.
     */
    public static boolean isModifier(Event e)
    {
        // SWT.MOD1 is supposed to be set to SWT.CONTROL on Windows and to
        // SWT.COMMAND on the Macintosh.
        return (e.stateMask & SWT.MOD1) == SWT.MOD1;
    }


    /**
     * Returns true if the key press involved a shift key.
     *
     * @return true if the key had a modifier set, false otherwise.
     */
    public static boolean isShift(Event e)
    {
        return (e.stateMask & SWT.SHIFT) == SWT.SHIFT;
    }


    public static KeyListener createCloseKeyListener(final Shell shell)
    {
        return new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) { return; }
            @Override
            public void keyPressed(KeyEvent e) 
            {
                if (isModifierKey(e) && e.keyCode == 'w')
                    shell.close();      // Will invoke shell close() listener.
            }
        };
    }


    public static Listener createEscapeKeyListener(final Shell shell)
    {
        return new Listener() {
            @Override
            public void handleEvent (final Event event)
            {
                if (event.detail == SWT.TRAVERSE_ESCAPE)
                    shell.close();
            }
        };
    }


    public static Listener createCancelKeyListener(final Shell shell)
    {
        return new Listener() {
            @Override
            public void handleEvent (final Event e)
            {
                if (isMacOSX())
                {
                    if (isModifier(e) && e.keyCode == '.')
                        shell.close();
                }
            }
        };
    }


    public static Listener createShellCloseListener(final Shell shell)
    {
        return new Listener() {
            @Override
            public void handleEvent(Event event)
            {
                shell.dispose();
            }
        };
    }


    /**
     * Add a MouseListener to all children of the given control.
     * Code based on http://stackoverflow.com/a/7226876/743730
     */
    public static void addMouseListenerRecursively(Control control,
                                                   MouseListener listener)
    {
        control.addMouseListener(listener);
        if (control instanceof Composite)
        {
            for (final Control c : ((Composite) control).getChildren())
                addMouseListenerRecursively(c, listener);
        }
    }


    public static void addKeyListenerRecursively(Control control,
                                                 Listener listener)
    {
        control.addListener(SWT.KeyDown, listener);
        if (control instanceof Composite)
        {
            for (final Control c : ((Composite) control).getChildren())
                addKeyListenerRecursively(c, listener);
        }
    }


    public static void addCancelKeyListenerRecursively(final Shell shell)
    {
        Listener listener = new Listener() {
            @Override
            public void handleEvent (final Event e)
            {
                if (isMacOSX())
                {
                    if (isModifier(e) && e.keyCode == '.')
                        shell.close();
                }
            }
        };
        addKeyListenerRecursively(shell, listener);
    }


    /**
     * Returns a font object based on the default button font.
     *
     * I don't understand why, but on Mac OS X, the label font size is not
     * the same as the button font size.  This looks wrong, as if it's our
     * mistake and not the defaults.  To compensate, we find out the button
     * font size and then set the labels to the same size.  This method
     * is a convenience function to get the font object.
     */
    public static final Font getDefaultLabelFont()
    {
        Shell tmpShell = new Shell();
        Button tmpButton = new Button(tmpShell, SWT.NONE);
        FontData[] tmpFontData = tmpButton.getFont().getFontData();
        tmpButton.dispose();
        tmpShell.dispose();
        final Font newFont = new Font(Display.getCurrent(), tmpFontData);
        Display.getCurrent().addListener(SWT.Dispose, new Listener() {
            public void handleEvent(Event event)
            {
                newFont.dispose();
            }
        });
        return newFont;
    }


    public static final Color getDefaultLabelForegroundColor()
    {
        Shell tmpShell = new Shell();
        Label tmpLabel = new Label(tmpShell, SWT.NONE);
        Color color = tmpLabel.getForeground();
        tmpLabel.dispose();
        tmpShell.dispose();
        return color;
    }


    public static final Color getInactiveTextColor()
    {
        return SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND);
    }


    public static final int getDefaultFontHeight()
    {
        Shell tmpShell = new Shell();
        Label tmpLabel = new Label(tmpShell, SWT.NONE);
        FontData[] tmpFontData = tmpLabel.getFont().getFontData();
        tmpLabel.dispose();
        tmpShell.dispose();
        return tmpFontData[0].getHeight();
    }


    /**
     * Takes a point size and returns an adjusted value based on the DPI of
     * the current device.  The numbers were empirically determined using two
     * systems with 72 and 96 dpi values.  The adjustments are not done on
     * Macs because something in the system (SWT? underlying Mac OS X font 
     * libraries? don't know) adjusts the sizes even for Retina displays.
     */
    public static final int scaledFontSize(int pt)
    {
        if (isMacOSX())
            return pt;
        
        final Device display = Display.getCurrent();
        final Point dpi      = display.getDPI();
        final Double scaled  = pt * 72.0/dpi.y * (0.0027777778 * dpi.y + 0.8);
        return scaled.intValue();
    }


    /**
     * Returns a font with a rescaled size based on the DPI of the current
     * device.
     */
    public static Font getFont(String name, int size, int style)
    {
        return SWTResourceManager.getFont(name, scaledFontSize(size), style);
    }


    public static Font getResizedFont(Font font, int sizeDifference)
    {
        if (font == null) return null;
        FontData[] fontData = font.getFontData();
        for (int i = 0; i < fontData.length; ++i)
            fontData[i].setHeight(fontData[i].getHeight() + sizeDifference);
        final Font newFont = new Font(font.getDevice(), fontData);
        Display.getCurrent().addListener(SWT.Dispose, new Listener() {
            public void handleEvent(Event event)
            {
                newFont.dispose();
            }
        });
        return newFont;
    }


    public static Font getResizedFont(String name, int style, int sizeDifference)
    {
        Shell tmpShell = new Shell();
        Label tmpLabel = new Label(tmpShell, SWT.NONE);
        FontData[] tmpFontData = tmpLabel.getFont().getFontData();
        int defaultHeight = tmpFontData[0].getHeight();
        Font font = SWTResourceManager.getFont(name, defaultHeight, style);
        if (font == null) return null;

        // Bogus, but I don't know what else to do about huge fonts on Ubuntu:
        if (isLinux() && defaultHeight >= 11) sizeDifference -= 2;

        FontData[] fontData = font.getFontData();
        for (int i = 0; i < fontData.length; ++i)
        {
            fontData[i].setHeight(fontData[i].getHeight() + sizeDifference);
            fontData[i].setStyle(style);
        }

        tmpLabel.dispose();
        tmpShell.dispose();
        final Font newFont = new Font(font.getDevice(), fontData);
        Display.getCurrent().addListener(SWT.Dispose, new Listener() {
            public void handleEvent(Event event)
            {
                newFont.dispose();
            }
        });
        return newFont;
    }


    /**
     * @return true if running on OS X
     */
    public final static boolean isMacOSX()
    {
        final String osName = System.getProperty("os.name");
        return osName.startsWith("Mac OS X");
    }


    /**
     * @return true if running on Linux
     */
    public final static boolean isLinux()
    {
        final String osName = System.getProperty("os.name");
        return osName.startsWith("Linux");
    }


    /**
     * @return true if running on Windows
     */
    public final static boolean isWindows()
    {
        final String osName = System.getProperty("os.name");
        return osName.startsWith("Windows");
    }


    /* The following is based on 
     * http://stackoverflow.com/questions/1351245/setting-swt-tooltip-delays
     * 
     * Unfortunately, it doesn't seem to work, and I don't know why.
     * The delay to the time the tooltip is shown doesn't seem to change.
     */
    
    final static int DEFAULT_HIDE_DELAY = 200;
    final static int DEFAULT_SHOW_DELAY = 100;

    public static void addCustomToolTip(Control control, String head, String body)
    {
        final ToolTip tip = new ToolTip(control.getShell(), SWT.BALLOON);
        final Display display = tip.getDisplay();
        tip.setText(head);
        tip.setMessage(body);
        tip.setAutoHide(false);

        control.addListener(SWT.MouseHover, new Listener() {
            public void handleEvent(Event event) {
                display.timerExec(DEFAULT_SHOW_DELAY, new Runnable() {
                    public void run() 
                    {
                        tip.setVisible(true);
                    }
                });             
            }
        });

        control.addListener(SWT.MouseExit, new Listener() {
            public void handleEvent(Event event) {
                display.timerExec(DEFAULT_HIDE_DELAY, new Runnable() {
                    public void run() 
                    {
                        tip.setVisible(false);
                    }
                });
            }
        });
    }

}
