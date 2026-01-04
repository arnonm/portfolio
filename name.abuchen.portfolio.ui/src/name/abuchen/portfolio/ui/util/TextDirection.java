package name.abuchen.portfolio.ui.util;

import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

public final class TextDirection 
{
    public enum Direction
    {
        LTR(SWT.LEFT_TO_RIGHT),
        RTL(SWT.RIGHT_TO_LEFT),
        AUTO(SWT.NONE);

        private final int swtConstant;

        Direction(int swtConstant)
        {
            this.swtConstant = swtConstant; 
        }       

        public int getSwtConstant()
        {
            return swtConstant;
        }
    }

    private static Direction userDirection = Direction.AUTO;

     private TextDirection()
    {
    }

    public static void setUserDirection(Direction direction)
    {
        userDirection = direction;
    }

    public static Direction getUserDirection()
    {
        return userDirection;
    }

    public static int getOrientation(Control control)
    {
        if (userDirection != Direction.AUTO)
        {
            System.out.println("TextDirection: User direction is set to " + userDirection); //$NON-NLS-1$
            return userDirection.getSwtConstant();
        }
        
        // Check if system locale is RTL
        String language = Locale.getDefault().getLanguage();
        System.out.println("TextDirection: Detected language (Locale.getDefault()) = " + language); //$NON-NLS-1$
        System.out.println("TextDirection: Full locale = " + Locale.getDefault()); //$NON-NLS-1$
        
        if (isSystemRTL())
        {
            System.out.println("TextDirection: Applying RTL orientation"); //$NON-NLS-1$
            return SWT.RIGHT_TO_LEFT;
        }
        
        int controlStyle = control.getStyle() & (SWT.LEFT_TO_RIGHT | SWT.RIGHT_TO_LEFT);
        System.out.println("TextDirection: Using control style = " + controlStyle); //$NON-NLS-1$
        return controlStyle;
    }

    public static boolean isRTL()
    {
        return userDirection == Direction.RTL || 
               (userDirection == Direction.AUTO && isSystemRTL());
    }

    private static boolean isSystemRTL()
    {
        // Check Eclipse osgi.nl property first (set by -nl argument)
        String osgiNl = System.getProperty("osgi.nl", ""); //$NON-NLS-1$//$NON-NLS-2$
        if (!osgiNl.isEmpty())
        {
            String language = osgiNl.length() >= 2 ? osgiNl.substring(0, 2) : osgiNl;
            System.out.println("TextDirection: Checking osgi.nl = " + osgiNl + ", language = " + language); //$NON-NLS-1$ //$NON-NLS-2$
            if (language.matches("^(ar|he|fa|ur|yi)$")) //$NON-NLS-1$
            {
                System.out.println("TextDirection: Detected RTL from osgi.nl"); //$NON-NLS-1$
                return true;
            }
        }
        
        // Fall back to Java locale
        String language = Locale.getDefault().getLanguage();
        return language.matches("^(ar|he|fa|ur|yi)$"); //$NON-NLS-1$
    }

}

