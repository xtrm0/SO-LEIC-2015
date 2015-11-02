/** @version $Id: Open.java,v 1.7 2015/11/01 23:02:59 ist181861 Exp $ */
package edt.textui.main;

import edt.core.Document;
import edt.core.DocumentWorker;
import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import java.io.File;

import java.io.IOException;

/**
 * Open existing document.
 */
public class Open extends Command<DocumentWorker> {
	public Open(DocumentWorker w) {
		super(MenuEntry.OPEN, w);
	}

	@Override
	public final void execute() throws DialogException, IOException {
		String inp = IO.readString(Message.openFile());
		File f = new File(inp);
		try {
			_receiver.loadDocument(f);
		} catch (Exception e) {
			//NOTE: This shows file not found everytime, even if the error was file not found.
			IO.println(Message.fileNotFound(inp));
		}
	}

}
