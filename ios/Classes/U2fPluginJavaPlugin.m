#import "U2fPluginJavaPlugin.h"
#if __has_include(<u2f_plugin_java/u2f_plugin_java-Swift.h>)
#import <u2f_plugin_java/u2f_plugin_java-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "u2f_plugin_java-Swift.h"
#endif

@implementation U2fPluginJavaPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftU2fPluginJavaPlugin registerWithRegistrar:registrar];
}
@end
