package de.pbauerochse.worklogviewer.settings;

import de.pbauerochse.worklogviewer.domain.ReportTimerange;
import de.pbauerochse.worklogviewer.settings.properties.NestedProperties;
import de.pbauerochse.worklogviewer.settings.properties.Property;

import java.io.File;
import java.time.DayOfWeek;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

/**
 * @author Patrick Bauerochse
 */
public class Settings {

    private static final File CONFIG_FILE_LOCATION = new File(System.getProperty("user.home"), "youtrack-worklog.properties");

    @NestedProperties
    private WindowSettings windowSettings = new WindowSettings();

    @NestedProperties
    private YoutrackSettings youtrackSettings = new YoutrackSettings();

    @Property("workhours")
    private int workHoursADay = 8;

    @Property("autoload.enabled")
    private boolean loadDataAtStartup = false;

    @Property("autoload.timerange")
    private ReportTimerange lastUsedReportTimerange = ReportTimerange.THIS_WEEK;

    @Property("statistics.enabled")
    private boolean showStatistics = true;

    @Property("showonlyowntimelogs.enabled")
    private boolean showAllWorklogs = true;

    @Property("excel.decimaltimes")
    private boolean showDecimalHourTimesInExcelReport = false;

    @Property("collapse.state")
    private int collapseState = createBitMaskState(SATURDAY, SUNDAY);

    @Property("highlight.state")
    private int highlightState = createBitMaskState(SATURDAY, SUNDAY);

    private static final Settings INSTANCE = SettingsFactory.loadSettings(CONFIG_FILE_LOCATION);

    public static Settings get() {
        return INSTANCE;
    }

    public static void save() {
        SettingsFactory.saveSettings(INSTANCE, CONFIG_FILE_LOCATION);
    }

    Settings() {

    }


    public int getWorkHoursADay() {
        return workHoursADay;
    }

    public void setWorkHoursADay(int workHoursADay) {
        this.workHoursADay = workHoursADay;
    }

    public boolean isLoadDataAtStartup() {
        return loadDataAtStartup;
    }

    public void setLoadDataAtStartup(boolean loadDataAtStartup) {
        this.loadDataAtStartup = loadDataAtStartup;
    }

    public ReportTimerange getLastUsedReportTimerange() {
        return lastUsedReportTimerange;
    }

    public void setLastUsedReportTimerange(ReportTimerange lastUsedReportTimerange) {
        this.lastUsedReportTimerange = lastUsedReportTimerange;
    }

    public boolean isShowStatistics() {
        return showStatistics;
    }

    public void setShowStatistics(boolean showStatistics) {
        this.showStatistics = showStatistics;
    }

    public boolean isShowAllWorklogs() {
        return showAllWorklogs;
    }

    public void setShowAllWorklogs(boolean showAllWorklogs) {
        this.showAllWorklogs = showAllWorklogs;
    }

    public boolean isShowDecimalHourTimesInExcelReport() {
        return showDecimalHourTimesInExcelReport;
    }

    public void setShowDecimalHourTimesInExcelReport(boolean showDecimalHourTimesInExcelReport) {
        this.showDecimalHourTimesInExcelReport = showDecimalHourTimesInExcelReport;
    }

    public int getCollapseState() {
        return collapseState;
    }

    public void setCollapseState(int collapseState) {
        this.collapseState = collapseState;
    }

    public int getHighlightState() {
        return highlightState;
    }

    public void setHighlightState(int highlightState) {
        this.highlightState = highlightState;
    }


    public boolean hasHighlightState(DayOfWeek day) {
        return hasBitValue(highlightState, day);
    }

    public boolean hasCollapseState(DayOfWeek day) {
        return hasBitValue(collapseState, day);
    }

    public void setHighlightState(DayOfWeek day, boolean selected) {
        highlightState = setBitValue(highlightState, day, selected);
    }

    public void setCollapseState(DayOfWeek day, boolean selected) {
        collapseState = setBitValue(collapseState, day, selected);
    }

    public WindowSettings getWindowSettings() {
        return windowSettings;
    }

    public YoutrackSettings getYoutrackSettings() {
        return youtrackSettings;
    }

    private static int createBitMaskState(DayOfWeek... setDays) {
        int bitmask = 0;

        for (DayOfWeek day : setDays) {
            bitmask = setBitValue(bitmask, day, true);
        }

        return bitmask;
    }

    private static int setBitValue(int state, DayOfWeek day, boolean selected) {
        if (selected) {
            return state | (1 << day.ordinal());
        } else {
            return state & ~(1 << day.ordinal());
        }
    }

    private static boolean hasBitValue(int state, DayOfWeek day) {
        int bitValue = (1 << day.ordinal());
        return (state & bitValue) == bitValue;
    }



}
