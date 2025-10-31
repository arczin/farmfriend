package gnu.text;

import java.io.Writer;

public class WriterManager implements Runnable {
    public static final WriterManager instance = new WriterManager();
    WriterRef first;

    public synchronized WriterRef register(Writer port) {
        WriterRef ref;
        ref = new WriterRef(port);
        WriterRef first2 = this.first;
        if (first2 != null) {
            ref.next = first2.next;
            first2.prev = ref;
        }
        this.first = ref;
        return ref;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void unregister(java.lang.Object r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r5 != 0) goto L_0x0005
            monitor-exit(r4)
            return
        L_0x0005:
            r0 = r5
            gnu.text.WriterRef r0 = (gnu.text.WriterRef) r0     // Catch:{ all -> 0x001c }
            gnu.text.WriterRef r1 = r0.next     // Catch:{ all -> 0x001c }
            gnu.text.WriterRef r2 = r0.prev     // Catch:{ all -> 0x001c }
            if (r1 == 0) goto L_0x0010
            r1.prev = r2     // Catch:{ all -> 0x001c }
        L_0x0010:
            if (r2 == 0) goto L_0x0014
            r2.next = r1     // Catch:{ all -> 0x001c }
        L_0x0014:
            gnu.text.WriterRef r3 = r4.first     // Catch:{ all -> 0x001c }
            if (r0 != r3) goto L_0x001a
            r4.first = r1     // Catch:{ all -> 0x001c }
        L_0x001a:
            monitor-exit(r4)
            return
        L_0x001c:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.WriterManager.unregister(java.lang.Object):void");
    }

    public synchronized void run() {
        for (WriterRef ref = this.first; ref != null; ref = ref.next) {
            Object port = ref.get();
            if (port != null) {
                try {
                    ((Writer) port).close();
                } catch (Exception e) {
                }
            }
        }
        this.first = null;
    }

    public boolean registerShutdownHook() {
        try {
            Runtime runtime = Runtime.getRuntime();
            runtime.getClass().getDeclaredMethod("addShutdownHook", new Class[]{Thread.class}).invoke(runtime, new Object[]{new Thread(this)});
            return true;
        } catch (Throwable th) {
            return false;
        }
    }
}
