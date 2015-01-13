import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;


public class DefFont extends Font{

	public DefFont(Font font) {
		super(font);
		// TODO Auto-generated constructor stub
	}

	public static Font derived(int size,double tracking){
		Font font = new Font("Arial", 0, size);
		Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
		attributes.put(TextAttribute.TRACKING, tracking);
		font = font.deriveFont(attributes);
		return font;
	}

}
