function exportData() {
    TableToExcel.convert(document.getElementById("job-table"), {
        name: "jobApplications.xlsx",
        sheet: {
            name: "Sheet 1",
        },
    });
}