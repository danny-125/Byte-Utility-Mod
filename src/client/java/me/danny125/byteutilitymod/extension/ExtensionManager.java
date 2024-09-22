package me.danny125.byteutilitymod.extension;

import me.danny125.byteutilitymod.Initialize;
import me.danny125.byteutilitymod.modules.Module;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ExtensionManager {
    public static void loadExtensions() {
        try {
            File extensions = new File("extensions");

            if (!extensions.exists()) {
                extensions.mkdir();
                return;
            }

            CopyOnWriteArrayList<File> extensionJars = new CopyOnWriteArrayList<>();
            for (File file : extensions.listFiles()) {
                if (file.getName().endsWith(".jar")) {
                    extensionJars.add(file);
                }
            }
            for (File extension : extensionJars) {
                URL loadPath = extension.toURI().toURL();
                URL[] classUrl = new URL[]{loadPath};

                try (URLClassLoader cl = new URLClassLoader(classUrl, ExtensionManager.class.getClassLoader())) {
                    JarFile jarFile = new JarFile(extension);
                    jarFile.stream().forEach(entry -> {
                        if (entry.getName().endsWith(".class")) {
                            String className = entry.getName().replace('/', '.').replace(".class", "");

                            try {
                                Class<?> clazz = cl.loadClass(className);

                                if (Module.class.isAssignableFrom(clazz)) {
                                    Module moduleInstance = (Module) clazz.getDeclaredConstructor().newInstance();
                                    Initialize.modules.add(moduleInstance);
                                }

                            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}