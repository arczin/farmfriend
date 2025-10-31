package com.google.appinventor.components.runtime;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventDispatcher {
    private static final boolean DEBUG = false;
    private static final Map<HandlesEventDispatching, EventRegistry> mapDispatchDelegateToEventRegistry = new HashMap();

    private static final class EventClosure {
        /* access modifiers changed from: private */
        public final String componentId;
        /* access modifiers changed from: private */
        public final String eventName;

        private EventClosure(String componentId2, String eventName2) {
            this.componentId = componentId2;
            this.eventName = eventName2;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EventClosure that = (EventClosure) o;
            if (this.componentId.equals(that.componentId) && this.eventName.equals(that.eventName)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.eventName.hashCode() * 31) + this.componentId.hashCode();
        }
    }

    private static final class EventRegistry {
        private final HandlesEventDispatching dispatchDelegate;
        /* access modifiers changed from: private */
        public final HashMap<String, Set<EventClosure>> eventClosuresMap = new HashMap<>();

        EventRegistry(HandlesEventDispatching dispatchDelegate2) {
            this.dispatchDelegate = dispatchDelegate2;
        }
    }

    private EventDispatcher() {
    }

    private static EventRegistry getEventRegistry(HandlesEventDispatching dispatchDelegate) {
        EventRegistry er = mapDispatchDelegateToEventRegistry.get(dispatchDelegate);
        if (er != null) {
            return er;
        }
        EventRegistry er2 = new EventRegistry(dispatchDelegate);
        mapDispatchDelegateToEventRegistry.put(dispatchDelegate, er2);
        return er2;
    }

    private static EventRegistry removeEventRegistry(HandlesEventDispatching dispatchDelegate) {
        return mapDispatchDelegateToEventRegistry.remove(dispatchDelegate);
    }

    public static synchronized void registerEventForDelegation(HandlesEventDispatching dispatchDelegate, String componentId, String eventName) {
        synchronized (EventDispatcher.class) {
            EventRegistry er = getEventRegistry(dispatchDelegate);
            Set<EventClosure> eventClosures = (Set) er.eventClosuresMap.get(eventName);
            Set<EventClosure> newEventClosures = eventClosures == null ? new HashSet<>() : new HashSet<>(eventClosures);
            newEventClosures.add(new EventClosure(componentId, eventName));
            er.eventClosuresMap.put(eventName, newEventClosures);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0048, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void unregisterEventForDelegation(com.google.appinventor.components.runtime.HandlesEventDispatching r7, java.lang.String r8, java.lang.String r9) {
        /*
            java.lang.Class<com.google.appinventor.components.runtime.EventDispatcher> r0 = com.google.appinventor.components.runtime.EventDispatcher.class
            monitor-enter(r0)
            com.google.appinventor.components.runtime.EventDispatcher$EventRegistry r1 = getEventRegistry(r7)     // Catch:{ all -> 0x0049 }
            java.util.HashMap r2 = r1.eventClosuresMap     // Catch:{ all -> 0x0049 }
            java.lang.Object r2 = r2.get(r9)     // Catch:{ all -> 0x0049 }
            java.util.Set r2 = (java.util.Set) r2     // Catch:{ all -> 0x0049 }
            if (r2 == 0) goto L_0x0047
            boolean r3 = r2.isEmpty()     // Catch:{ all -> 0x0049 }
            if (r3 == 0) goto L_0x001a
            goto L_0x0047
        L_0x001a:
            java.util.HashSet r3 = new java.util.HashSet     // Catch:{ all -> 0x0049 }
            r3.<init>()     // Catch:{ all -> 0x0049 }
            java.util.Iterator r4 = r2.iterator()     // Catch:{ all -> 0x0049 }
        L_0x0023:
            boolean r5 = r4.hasNext()     // Catch:{ all -> 0x0049 }
            if (r5 == 0) goto L_0x003e
            java.lang.Object r5 = r4.next()     // Catch:{ all -> 0x0049 }
            com.google.appinventor.components.runtime.EventDispatcher$EventClosure r5 = (com.google.appinventor.components.runtime.EventDispatcher.EventClosure) r5     // Catch:{ all -> 0x0049 }
            java.lang.String r6 = r5.componentId     // Catch:{ all -> 0x0049 }
            boolean r6 = r6.equals(r8)     // Catch:{ all -> 0x0049 }
            if (r6 == 0) goto L_0x003a
            goto L_0x003d
        L_0x003a:
            r3.add(r5)     // Catch:{ all -> 0x0049 }
        L_0x003d:
            goto L_0x0023
        L_0x003e:
            java.util.HashMap r4 = r1.eventClosuresMap     // Catch:{ all -> 0x0049 }
            r4.put(r9, r3)     // Catch:{ all -> 0x0049 }
            monitor-exit(r0)
            return
        L_0x0047:
            monitor-exit(r0)
            return
        L_0x0049:
            r7 = move-exception
            monitor-exit(r0)
            goto L_0x004d
        L_0x004c:
            throw r7
        L_0x004d:
            goto L_0x004c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.EventDispatcher.unregisterEventForDelegation(com.google.appinventor.components.runtime.HandlesEventDispatching, java.lang.String, java.lang.String):void");
    }

    public static synchronized void unregisterAllEventsForDelegation() {
        synchronized (EventDispatcher.class) {
            for (EventRegistry er : mapDispatchDelegateToEventRegistry.values()) {
                er.eventClosuresMap.clear();
            }
        }
    }

    public static synchronized void removeDispatchDelegate(HandlesEventDispatching dispatchDelegate) {
        synchronized (EventDispatcher.class) {
            EventRegistry er = removeEventRegistry(dispatchDelegate);
            if (er != null) {
                er.eventClosuresMap.clear();
            }
        }
    }

    public static synchronized boolean dispatchEvent(Component component, String eventName, Object... args) {
        boolean z;
        synchronized (EventDispatcher.class) {
            z = Boolean.TRUE == dispatchFallibleEvent(component, eventName, args);
        }
        return z;
    }

    public static synchronized Object dispatchFallibleEvent(Component component, String eventName, Object... args) {
        Boolean valueOf;
        synchronized (EventDispatcher.class) {
            boolean dispatched = false;
            try {
                Object[] args2 = OptionHelper.optionListsFromValues(component, eventName, args);
                HandlesEventDispatching dispatchDelegate = component.getDispatchDelegate();
                if (dispatchDelegate.canDispatchEvent(component, eventName)) {
                    Set<EventClosure> eventClosures = (Set) getEventRegistry(dispatchDelegate).eventClosuresMap.get(eventName);
                    if (eventClosures != null && eventClosures.size() > 0) {
                        dispatched = delegateDispatchEvent(dispatchDelegate, eventClosures, component, args2);
                    }
                    dispatchDelegate.dispatchGenericEvent(component, eventName, !dispatched, args2);
                }
                valueOf = Boolean.valueOf(dispatched);
            } catch (Exception e) {
                return e;
            }
        }
        return valueOf;
    }

    private static synchronized boolean delegateDispatchEvent(HandlesEventDispatching dispatchDelegate, Set<EventClosure> eventClosures, Component component, Object... args) {
        boolean dispatched;
        synchronized (EventDispatcher.class) {
            dispatched = false;
            for (EventClosure eventClosure : eventClosures) {
                if (dispatchDelegate.dispatchEvent(component, eventClosure.componentId, eventClosure.eventName, args)) {
                    dispatched = true;
                }
            }
        }
        return dispatched;
    }

    public static String makeFullEventName(String componentId, String eventName) {
        return componentId + "$" + eventName;
    }
}
