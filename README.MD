# TextReportGenerator
<p align="center">
  <img src="resources/logoEII.png" width="200" alt="EII Logo">
  <img src="resources/logoUniOvi.png" width="200" alt="UniOvi Logo">
</p>
<p align="center">

</p>

# What is it?
This tool generates reports using the database created with it. This is useful to create similar reports with variability because you can add dependencies when you create the database.

# Steps of use
1.- Second tab
1.1.- Create the database by adding: name, introduction text (if needed), conclusion text (if needed), and options. You can create as many options as you prefer, their probability to appear (greater number, more options according to the rest options), dependencies (if this option appears it creates a dependency, i.e. Windows Operating System), depends on (if this option can just appears if a previous dependency appeared, i.e. Windows Operating System).
1.2.- You can save and load the database here to modify.
2.- First tab
2.1.- Load the created database
2.2.- Generate text/s. If you have many options, you can create many different texts with just one click.
2.3.- If you have configured in the third tab an SMTP server you can send the texts by email.

# Executable
- You need Java 8 for version <= 0.9.1, or Java 17 for version 0.9.2.
- You can find the releases in the folder [release](https://github.com/gonzalezgarciacristian/TextReportGenerator/tree/dev/release).
- You can download the project and generate a new Jar file using Eclipse, or use Eclipse to launch this tool.
- You can download a release and create your database or use one of us.

# License
This program is distributed under the [GNU General Public License Version 3](https://github.com/gonzalezgarciacristian/TextReportGenerator/blob/dev/LICENSE)