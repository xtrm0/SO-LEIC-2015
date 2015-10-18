/** @version $Id: InsertSection.java,v 1.2 2015/10/17 19:58:54 ist181861 Exp $ */
package edt.textui.section;

import edt.core.Section;
import edt.core.Document;
import static ist.po.ui.Dialog.IO;
import ist.po.ui.DialogException;

import java.io.IOException;

/**
 * §2.2.5.
 */
public class InsertSection extends SectionCommand {
  public InsertSection(Section s, Document w) {
    super(MenuEntry.INSERT_SECTION, s, w);
  }

  @Override
  public final void execute() throws DialogException, IOException {
    int sectId = IO.readInteger(Message.requestSectionId());
    String title = IO.readString(Message.requestSectionTitle());
    if (sectId < 0 || sectId >= _receiver.getSectionsCount()) {
			sectId = _receiver.getSectionsCount();
		}
    _receiver.insertSection(title, sectId);
  }
}
