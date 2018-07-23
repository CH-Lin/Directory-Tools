package ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.swing.JList;
import javax.swing.JScrollPane;

public class FileListScrollPane extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JList<String> list_source;

	private Thread minotor = null;

	public FileListScrollPane() {
		list_source = new JList<>();
		setViewportView(list_source);
	}

	public void setListData(final File f) {
		// list_selected.setListData(new String[0]);

		if (f.isDirectory()) {
			list_source.setListData(f.list());

			if (minotor != null) {
				minotor.interrupt();
				minotor = null;
			}

			minotor = new Thread(new Runnable() {

				public void run() {
					try {
						WatchService watcher = FileSystems.getDefault().newWatchService();
						Path dir = f.toPath();

						WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
								StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

						for (;;) {

							try {
								key = watcher.take();
							} catch (InterruptedException x) {
								return;
							}

							for (WatchEvent<?> event : key.pollEvents()) {
								WatchEvent.Kind<?> kind = event.kind();

								if (kind == StandardWatchEventKinds.OVERFLOW) {
									continue;
								}

								else if (kind == StandardWatchEventKinds.ENTRY_CREATE
										|| kind == StandardWatchEventKinds.ENTRY_DELETE
										|| kind == StandardWatchEventKinds.ENTRY_MODIFY) {
								}
								list_source.setListData(f.list());

							}

							boolean valid = key.reset();
							if (!valid) {
								break;
							}
						}
					} catch (IOException x) {
						System.err.println(x);
					}
				}
			});

			minotor.start();

		} else {
			String list[] = new String[] { f.getName() };
			list_source.setListData(list);
		}
	}
}
