/**
 * Oshi (https://github.com/oshi/oshi)
 *
 * Copyright (c) 2010 - 2018 The Oshi Project Team
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Maintainers: dblock[at]dblock[dot]org widdis[at]gmail[dot]com
 * enrico.bianchi[at]gmail[dot]com
 *
 * Contributors: https://github.com/oshi/oshi/graphs/contributors
 */
package gestiondeprocesos;

import java.util.Arrays;
import java.util.List;
import static junit.framework.Assert.assertFalse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.PlatformEnum;
import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.Display;
import oshi.hardware.Firmware;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.hardware.PowerSource;
import oshi.hardware.Sensors;
import oshi.hardware.SoundCard;
import oshi.hardware.UsbDevice;
import oshi.software.os.FileSystem;
import oshi.software.os.NetworkParams;
import oshi.software.os.OSFileStore;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

/**
 *
 * @author Arnold_Irias
 */
public class InformacionHSSistema {
    //System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");
    //System.setProperty(org.slf4j.impl.SimpleLogger.LOG_FILE_KEY, "System.err");

    Logger LOG = LoggerFactory.getLogger(InformacionHSSistema.class);
    SystemInfo si = new SystemInfo();
    HardwareAbstractionLayer hal = si.getHardware();
    OperatingSystem os = si.getOperatingSystem();

    private String sistema_operativo;

    private String pc_manufacturer;
    private String pc_modelo;
    private String pc_serie;

    private String firmware_manufacturer;
    private String firmware_nombre;
    private String firmware_descripcion;
    private String firmware_version;
    private String firmware_realease_date;

    private String placa_manufacturer;
    private String placa_modelo;
    private String placa_serie;
    private String placa_version;

    private int procesador_fisico;
    private int procesador_nucleos;
    private int procesadores_logicos;
    private String procesador_identificador;
    private String procesador_id;

    private String memoria_disponible;
    private String memoria_total;
    private long memoria_tamtotal;

    private String swap_disponible;
    private String swap_total;

    private String tiempoServicio;
    private double usoCPUxNucleo;

    private int numProcesos;
    private int numHilos;

    public void testCentralProcessor() {
        assertFalse(PlatformEnum.UNKNOWN.equals(SystemInfo.getCurrentPlatformEnum()));
    }

    public void generarInf() {
        setSistema_operativo(os.toString());
        //System.out.println(sistema_operativo);

        // LOG.info("Checking computer system...");
        printComputerSystem(hal.getComputerSystem());

        //   LOG.info("Checking Processor...");
        printProcessor(hal.getProcessor());

        /*
         //   LOG.info("Checking Sensors...");
         // printSensors(hal.getSensors());

         //   LOG.info("Checking Power sources...");
         printPowerSources(hal.getPowerSources());

         //   LOG.info("Checking Disks...");
         printDisks(hal.getDiskStores());

         //   LOG.info("Checking File System...");
         printFileSystem(os.getFileSystem());

         //   LOG.info("Checking Network interfaces...");
         printNetworkInterfaces(hal.getNetworkIFs());

         //   LOG.info("Checking Network parameterss...");
         printNetworkParameters(os.getNetworkParams());

         // hardware: displays
         //   LOG.info("Checking Displays...");
         printDisplays(hal.getDisplays());

         // hardware: USB devices
         //   LOG.info("Checking USB Devices...");
         printUsbDevices(hal.getUsbDevices(true));

         //   LOG.info("Checking Sound Cards...");
         printSoundCards(hal.getSoundCards());
         */
    }

    private void printComputerSystem(ComputerSystem computerSystem) {

        setPc_manufacturer(computerSystem.getManufacturer());
        setPc_modelo(computerSystem.getModel());
        setPc_serie(computerSystem.getSerialNumber());

        final Firmware firmware = computerSystem.getFirmware();
        setFirmware_manufacturer(firmware.getManufacturer());
        setFirmware_nombre(firmware.getName());
        setFirmware_version(firmware.getVersion());
        setFirmware_realease_date(firmware.getReleaseDate() == null ? "unknown"
                : firmware.getReleaseDate() == null ? "unknown" : firmware.getReleaseDate());
        setFirmware_descripcion(firmware.getDescription());

        final Baseboard baseboard = computerSystem.getBaseboard();
        setPlaca_manufacturer(baseboard.getManufacturer());
        setPlaca_modelo(baseboard.getModel());
        setPlaca_version(baseboard.getVersion());
        setPlaca_serie(baseboard.getSerialNumber());

        /*
         System.out.println("manufacturer: " + computerSystem.getManufacturer());
         System.out.println("model: " + computerSystem.getModel());
         System.out.println("serialnumber: " + computerSystem.getSerialNumber());

         System.out.println("firmware:");
         System.out.println("  manufacturer: " + firmware.getManufacturer());
         System.out.println("  name: " + firmware.getName());
         System.out.println("  description: " + firmware.getDescription());
         System.out.println("  version: " + firmware.getVersion());
         System.out.println("  release date: " + (firmware.getReleaseDate() == null ? "unknown"
         : firmware.getReleaseDate() == null ? "unknown" : firmware.getReleaseDate()));

         System.out.println("baseboard:");
         System.out.println("  manufacturer: " + baseboard.getManufacturer());
         System.out.println("  model: " + baseboard.getModel());
         System.out.println("  version: " + baseboard.getVersion());
         System.out.println("  serialnumber: " + baseboard.getSerialNumber());
         /*if (Util.identifyVM().length() > 0) {
         System.out.println("virtualization: " + Util.identifyVM());
         }*/
    }

    private void printProcessor(CentralProcessor processor) {

        setProcesador_fisico(processor.getPhysicalPackageCount());
        setProcesador_nucleos(processor.getPhysicalProcessorCount());
        setProcesadores_logicos(processor.getLogicalProcessorCount());
        setProcesador_identificador(processor.getIdentifier());
        setProcesador_id(processor.getProcessorID());

        /*System.out.println(processor);
         System.out.println(" " + processor.getPhysicalPackageCount() + " physical CPU package(s)");
         System.out.println(" " + processor.getPhysicalProcessorCount() + " physical CPU core(s)");
         System.out.println(" " + processor.getLogicalProcessorCount() + " logical CPU(s)");

         System.out.println("Identifier: " + processor.getIdentifier());
         System.out.println("ProcessorID: " + processor.getProcessorID());
         */
    }

    private void usoMemoria() {

        //   LOG.info("Checking Memory...");
        GlobalMemory memory = hal.getMemory();

        setMemoria_disponible(FormatUtil.formatBytes(memory.getAvailable()));
        setMemoria_total(FormatUtil.formatBytes(memory.getTotal()));
        setMemoria_tamtotal(memory.getTotal());
        setSwap_disponible(FormatUtil.formatBytes(memory.getSwapUsed()));
        setSwap_total(FormatUtil.formatBytes(memory.getSwapTotal()));

        /*System.out.println("Memory: " + FormatUtil.formatBytes(memory.getAvailable()) + "/"
         + FormatUtil.formatBytes(memory.getTotal()));
         System.out.println("Swap used: " + FormatUtil.formatBytes(memory.getSwapUsed()) + "/"
         + FormatUtil.formatBytes(memory.getSwapTotal()));
         */
    }

    public void actualizar() {
        usoMemoria();
        usoCpu();

    }

    private void usoCpu() {

        //   LOG.info("Checking CPU...");
        CentralProcessor processor = hal.getProcessor();
        setTiempoServicio(FormatUtil.formatElapsedSecs(processor.getSystemUptime()));
        StringBuilder procCpu = new StringBuilder("CPU load per processor:");
        double[] load = processor.getProcessorCpuLoadBetweenTicks();
        double usoCPU = 0;
        for (double avg : load) {
            procCpu.append(String.format(" %.1f%%", avg * 100));
            usoCPU += avg * 100;
        }

        setUsoCPUxNucleo(usoCPU / getProcesadores_logicos());

        /*
         System.out.println(procCpu.toString());
         System.out.println("Uptime: " + FormatUtil.formatElapsedSecs(processor.getSystemUptime()));
         System.out.println(
         "Context Switches/Interrupts: " + processor.getContextSwitches() + " / " + processor.getInterrupts());
         long[] prevTicks = processor.getSystemCpuLoadTicks();
         System.out.println("CPU, IOWait, and IRQ ticks @ 0 sec:" + Arrays.toString(prevTicks));
         // Wait a second...
         Util.sleep(1000);
         long[] ticks = processor.getSystemCpuLoadTicks();
         System.out.println("CPU, IOWait, and IRQ ticks @ 1 sec:" + Arrays.toString(ticks));
         long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
         long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
         long sys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
         long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
         long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
         long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
         long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
         long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
         long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;

         System.out.format(
         "User: %.1f%% Nice: %.1f%% System: %.1f%% Idle: %.1f%% IOwait: %.1f%% IRQ: %.1f%% SoftIRQ: %.1f%% Steal: %.1f%%%n",
         100d * user / totalCpu, 100d * nice / totalCpu, 100d * sys / totalCpu, 100d * idle / totalCpu,
         100d * iowait / totalCpu, 100d * irq / totalCpu, 100d * softirq / totalCpu, 100d * steal / totalCpu);
         System.out.format("CPU load: %.1f%% (counting ticks)%n", processor.getSystemCpuLoadBetweenTicks() * 100);
         System.out.format("CPU load: %.1f%% (OS MXBean)%n", processor.getSystemCpuLoad() * 100);
         double[] loadAverage = processor.getSystemLoadAverage(3);
         System.out.println("CPU load averages:" + (loadAverage[0] < 0 ? " N/A" : String.format(" %.2f", loadAverage[0]))
         + (loadAverage[1] < 0 ? " N/A" : String.format(" %.2f", loadAverage[1]))
         + (loadAverage[2] < 0 ? " N/A" : String.format(" %.2f", loadAverage[2])));
         // per core CPU
         
         */
    }

    public List<OSProcess> obtenerProcesos() {

        //   LOG.info("Checking Processes...");
        GlobalMemory memory = hal.getMemory();
        setNumProcesos(os.getProcessCount());
        setNumHilos(os.getThreadCount());

        // Sort by highest CPU
        List<OSProcess> procs = Arrays.asList(os.getProcesses(os.getProcessCount(), OperatingSystem.ProcessSort.CPU, false));

        //System.out.println("Processes: " + os.getProcessCount() + ", Threads: " + os.getThreadCount());
        //System.out.println("   PID  %CPU %MEM       VSZ       RSS Name");
        for (int i = 0; i < procs.size(); i++) {
            OSProcess p = procs.get(i);
            /*System.out.format(" %5d %5.1f %4.1f %9s %9s %s%n", p.getProcessID(),
             100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime(),
             100d * p.getResidentSetSize() / memory.getTotal(), FormatUtil.formatBytes(p.getVirtualSize()),
             FormatUtil.formatBytes(p.getResidentSetSize()), p.getName());
             */
        }

        return procs;
    }

    private static void printSensors(Sensors sensors) {
        System.out.println("Sensors:");
        System.out.format(" CPU Temperature: %.1f°C%n", sensors.getCpuTemperature());
        System.out.println(" Fan Speeds: " + Arrays.toString(sensors.getFanSpeeds()));
        System.out.format(" CPU Voltage: %.1fV%n", sensors.getCpuVoltage());
    }

    private static void printPowerSources(PowerSource[] powerSources) {
        StringBuilder sb = new StringBuilder("Power: ");
        if (powerSources.length == 0) {
            sb.append("Unknown");
        } else {
            double timeRemaining = powerSources[0].getTimeRemaining();
            if (timeRemaining < -1d) {
                sb.append("Charging");
            } else if (timeRemaining < 0d) {
                sb.append("Calculating time remaining");
            } else {
                sb.append(String.format("%d:%02d remaining", (int) (timeRemaining / 3600),
                        (int) (timeRemaining / 60) % 60));
            }
        }
        for (PowerSource pSource : powerSources) {
            sb.append(String.format("%n %s @ %.1f%%", pSource.getName(), pSource.getRemainingCapacity() * 100d));
        }
        System.out.println(sb.toString());
    }

    private static void printDisks(HWDiskStore[] diskStores) {
        System.out.println("Disks:");
        for (HWDiskStore disk : diskStores) {
            boolean readwrite = disk.getReads() > 0 || disk.getWrites() > 0;
            System.out.format(" %s: (model: %s - S/N: %s) size: %s, reads: %s (%s), writes: %s (%s), xfer: %s ms%n",
                    disk.getName(), disk.getModel(), disk.getSerial(),
                    disk.getSize() > 0 ? FormatUtil.formatBytesDecimal(disk.getSize()) : "?",
                    readwrite ? disk.getReads() : "?", readwrite ? FormatUtil.formatBytes(disk.getReadBytes()) : "?",
                    readwrite ? disk.getWrites() : "?", readwrite ? FormatUtil.formatBytes(disk.getWriteBytes()) : "?",
                    readwrite ? disk.getTransferTime() : "?");
            HWPartition[] partitions = disk.getPartitions();
            if (partitions == null) {
                // TODO Remove when all OS's implemented
                continue;
            }
            for (HWPartition part : partitions) {
                System.out.format(" |-- %s: %s (%s) Maj:Min=%d:%d, size: %s%s%n", part.getIdentification(),
                        part.getName(), part.getType(), part.getMajor(), part.getMinor(),
                        FormatUtil.formatBytesDecimal(part.getSize()),
                        part.getMountPoint().isEmpty() ? "" : " @ " + part.getMountPoint());
            }
        }
    }

    private static void printFileSystem(FileSystem fileSystem) {
        System.out.println("File System:");

        System.out.format(" File Descriptors: %d/%d%n", fileSystem.getOpenFileDescriptors(),
                fileSystem.getMaxFileDescriptors());

        OSFileStore[] fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long usable = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            System.out.format(
                    " %s (%s) [%s] %s of %s free (%.1f%%), %s of %s files free (%.1f%%) is %s "
                    + (fs.getLogicalVolume() != null && fs.getLogicalVolume().length() > 0 ? "[%s]" : "%s")
                    + " and is mounted at %s%n",
                    fs.getName(), fs.getDescription().isEmpty() ? "file system" : fs.getDescription(), fs.getType(),
                    FormatUtil.formatBytes(usable), FormatUtil.formatBytes(fs.getTotalSpace()), 100d * usable / total,
                    fs.getFreeInodes(), fs.getTotalInodes(), 100d * fs.getFreeInodes() / fs.getTotalInodes(),
                    fs.getVolume(), fs.getLogicalVolume(), fs.getMount());
        }
    }

    private static void printNetworkInterfaces(NetworkIF[] networkIFs) {
        System.out.println("Network interfaces:");
        for (NetworkIF net : networkIFs) {
            System.out.format(" Name: %s (%s)%n", net.getName(), net.getDisplayName());
            System.out.format("   MAC Address: %s %n", net.getMacaddr());
            System.out.format("   MTU: %s, Speed: %s %n", net.getMTU(), FormatUtil.formatValue(net.getSpeed(), "bps"));
            System.out.format("   IPv4: %s %n", Arrays.toString(net.getIPv4addr()));
            System.out.format("   IPv6: %s %n", Arrays.toString(net.getIPv6addr()));
            boolean hasData = net.getBytesRecv() > 0 || net.getBytesSent() > 0 || net.getPacketsRecv() > 0
                    || net.getPacketsSent() > 0;
            System.out.format("   Traffic: received %s/%s%s; transmitted %s/%s%s %n",
                    hasData ? net.getPacketsRecv() + " packets" : "?",
                    hasData ? FormatUtil.formatBytes(net.getBytesRecv()) : "?",
                    hasData ? " (" + net.getInErrors() + " err)" : "",
                    hasData ? net.getPacketsSent() + " packets" : "?",
                    hasData ? FormatUtil.formatBytes(net.getBytesSent()) : "?",
                    hasData ? " (" + net.getOutErrors() + " err)" : "");
        }
    }

    private static void printNetworkParameters(NetworkParams networkParams) {
        System.out.println("Network parameters:");
        System.out.format(" Host name: %s%n", networkParams.getHostName());
        System.out.format(" Domain name: %s%n", networkParams.getDomainName());
        System.out.format(" DNS servers: %s%n", Arrays.toString(networkParams.getDnsServers()));
        System.out.format(" IPv4 Gateway: %s%n", networkParams.getIpv4DefaultGateway());
        System.out.format(" IPv6 Gateway: %s%n", networkParams.getIpv6DefaultGateway());
    }

    private static void printDisplays(Display[] displays) {
        System.out.println("Displays:");
        int i = 0;
        for (Display display : displays) {
            System.out.println(" Display " + i + ":");
            System.out.println(display.toString());
            i++;
        }
    }

    private static void printUsbDevices(UsbDevice[] usbDevices) {
        System.out.println("USB Devices:");
        for (UsbDevice usbDevice : usbDevices) {
            System.out.println(usbDevice.toString());
        }
    }

    private static void printSoundCards(SoundCard[] cards) {
        System.out.println("Sound Cards:");
        for (SoundCard card : cards) {
            System.out.println(card.toString());
        }
    }

    public String getSistema_operativo() {
        return sistema_operativo;
    }

    public void setSistema_operativo(String sistema_operativo) {
        this.sistema_operativo = sistema_operativo;
    }

    public String getPc_manufacturer() {
        return pc_manufacturer;
    }

    public void setPc_manufacturer(String pc_manufacturer) {
        this.pc_manufacturer = pc_manufacturer;
    }

    public String getPc_modelo() {
        return pc_modelo;
    }

    public void setPc_modelo(String pc_modelo) {
        this.pc_modelo = pc_modelo;
    }

    public String getPc_serie() {
        return pc_serie;
    }

    public void setPc_serie(String pc_serie) {
        this.pc_serie = pc_serie;
    }

    public String getFirmware_manufacturer() {
        return firmware_manufacturer;
    }

    public void setFirmware_manufacturer(String firmware_manufacturer) {
        this.firmware_manufacturer = firmware_manufacturer;
    }

    public String getFirmware_nombre() {
        return firmware_nombre;
    }

    public void setFirmware_nombre(String firmware_nombre) {
        this.firmware_nombre = firmware_nombre;
    }

    public String getFirmware_descripcion() {
        return firmware_descripcion;
    }

    public void setFirmware_descripcion(String firmware_descripcion) {
        this.firmware_descripcion = firmware_descripcion;
    }

    public String getFirmware_version() {
        return firmware_version;
    }

    public void setFirmware_version(String firmware_version) {
        this.firmware_version = firmware_version;
    }

    public String getFirmware_realease_date() {
        return firmware_realease_date;
    }

    public void setFirmware_realease_date(String firmware_realease_date) {
        this.firmware_realease_date = firmware_realease_date;
    }

    public String getPlaca_manufacturer() {
        return placa_manufacturer;
    }

    public void setPlaca_manufacturer(String placa_manufacturer) {
        this.placa_manufacturer = placa_manufacturer;
    }

    public String getPlaca_modelo() {
        return placa_modelo;
    }

    public void setPlaca_modelo(String placa_modelo) {
        this.placa_modelo = placa_modelo;
    }

    public String getPlaca_serie() {
        return placa_serie;
    }

    public void setPlaca_serie(String placa_serie) {
        this.placa_serie = placa_serie;
    }

    public String getPlaca_version() {
        return placa_version;
    }

    public void setPlaca_version(String placa_version) {
        this.placa_version = placa_version;
    }

    public int getProcesador_fisico() {
        return procesador_fisico;
    }

    public void setProcesador_fisico(int procesador_fisico) {
        this.procesador_fisico = procesador_fisico;
    }

    public int getProcesador_nucleos() {
        return procesador_nucleos;
    }

    public void setProcesador_nucleos(int procesador_nucleos) {
        this.procesador_nucleos = procesador_nucleos;
    }

    public int getProcesadores_logicos() {
        return procesadores_logicos;
    }

    public void setProcesadores_logicos(int procesadores_logicos) {
        this.procesadores_logicos = procesadores_logicos;
    }

    public String getProcesador_identificador() {
        return procesador_identificador;
    }

    public void setProcesador_identificador(String procesador_identificador) {
        this.procesador_identificador = procesador_identificador;
    }

    public String getProcesador_id() {
        return procesador_id;
    }

    public void setProcesador_id(String procesador_id) {
        this.procesador_id = procesador_id;
    }

    public String getMemoria_disponible() {
        return memoria_disponible;
    }

    public void setMemoria_disponible(String memoria_disponible) {
        this.memoria_disponible = memoria_disponible;
    }

    public String getMemoria_total() {
        return memoria_total;
    }

    public void setMemoria_total(String memoria_total) {
        this.memoria_total = memoria_total;
    }

    public String getSwap_disponible() {
        return swap_disponible;
    }

    public void setSwap_disponible(String swap_disponible) {
        this.swap_disponible = swap_disponible;
    }

    public String getSwap_total() {
        return swap_total;
    }

    public void setSwap_total(String swap_total) {
        this.swap_total = swap_total;
    }

    public String getTiempoServicio() {
        return tiempoServicio;
    }

    public void setTiempoServicio(String tiempoServicio) {
        this.tiempoServicio = tiempoServicio;
    }

    public int getNumProcesos() {
        return numProcesos;
    }

    public void setNumProcesos(int numProcesos) {
        this.numProcesos = numProcesos;
    }

    public int getNumHilos() {
        return numHilos;
    }

    public void setNumHilos(int numHilos) {
        this.numHilos = numHilos;
    }

    public long getMemoria_tamtotal() {
        return memoria_tamtotal;
    }

    public void setMemoria_tamtotal(long memoria_tamtotal) {
        this.memoria_tamtotal = memoria_tamtotal;
    }

    public void setUsoCPUxNucleo(double usoCPUxNucleo) {
        this.usoCPUxNucleo = usoCPUxNucleo;
    }

    public double getUsoCPUxNucleo() {
        return usoCPUxNucleo;
    }

}
