import SwiftUI
import FirebaseCore

@main
struct iOSApp: App {
    init() {
        // GitLive's multiplatform Firebase SDK calls into the native iOS SDK,
        // so it must be configured here before any Compose / repository code runs.
        FirebaseApp.configure()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
