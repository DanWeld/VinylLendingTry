# Network Test Setup Script

# Compile all Java files
Write-Host "Compiling Java files..."
javac -d bin -cp "lib/*;src/main/java" src/main/java/com/vinyl/server/RunServer.java
javac -d bin -cp "lib/*;src/main/java" src/main/java/com/vinyl/client/RunClient.java

# Get the IP address
$ipAddress = (Get-NetIPAddress -AddressFamily IPv4 -InterfaceAlias Ethernet).IPAddress
if (-not $ipAddress) {
    $ipAddress = (Get-NetIPAddress -AddressFamily IPv4 -InterfaceAlias Wi-Fi).IPAddress
}

Write-Host "Your IP address is: $ipAddress"
Write-Host "`nWireshark Setup Instructions:"
Write-Host "1. Open Wireshark"
Write-Host "2. Select your network interface (Ethernet or Wi-Fi)"
Write-Host "3. Enter this capture filter: tcp port 2910"
Write-Host "4. Click 'Start Capturing'"

Write-Host "`nTo start the server, run:"
Write-Host "java -cp 'bin;lib/*' com.vinyl.server.RunServer"

Write-Host "`nTo start the client on another machine, first set the server IP:"
Write-Host "java -cp 'bin;lib/*' com.vinyl.client.network.ClientConfig 'setServerHost' '$ipAddress'"
Write-Host "Then run the client:"
Write-Host "java -cp 'bin;lib/*' --module-path 'lib/javafx-sdk-17.0.1/lib' --add-modules javafx.controls,javafx.fxml com.vinyl.client.RunClient"

Write-Host "`nTest Scenario:"
Write-Host "1. Start Wireshark capture"
Write-Host "2. Start the server"
Write-Host "3. Start the client on another machine"
Write-Host "4. Log in and perform a borrow operation"
Write-Host "5. Check Wireshark for:"
Write-Host "   - TCP 3-way handshake (SYN, SYN-ACK, ACK)"
Write-Host "   - Borrow request message"
Write-Host "   - Server response" 