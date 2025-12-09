package name.abuchen.portfolio.ui.util;

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
            return userDirection.getSwtConstant();
        
        return control.getStyle() & (SWT.LEFT_TO_RIGHT | SWT.RIGHT_TO_LEFT);
    }

    public static boolean isRTL()
    {
        return userDirection == Direction.RTL || 
               (userDirection == Direction.AUTO && isSystemRTL());
    }

    private static boolean isSystemRTL()
    {
        String language = System.getProperty("user.language", "");
        return language.matches("^(ar|he|fa|ur|yi)$");
    }

}

