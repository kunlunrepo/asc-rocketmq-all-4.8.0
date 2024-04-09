/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.srvutil;

import java.util.Properties;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ServerUtil {

    // 构建命令行参数
    public static Options buildCommandlineOptions(final Options options) {
        /**
         * "h": 短选项标识符，用户在命令行中可以使用-h来指定该选项。
         * "help": 长选项标识符，用户也可以使用--help来指定相同选项。
         * false: 表示该选项不需要额外的参数值，即它是无参选项。
         * "Print help": 提供该选项的描述信息，说明其作用是打印帮助信息
         */
        Option opt = new Option("h", "help", false, "Print help");
        opt.setRequired(false); // 该选项标记为非必需选项
        options.addOption(opt);

        opt =
            new Option("n", "namesrvAddr", true,
                "Name server address list, eg: 192.168.0.1:9876;192.168.0.2:9876");
        opt.setRequired(false);
        options.addOption(opt);

        return options;
    }

    // 解析命令行参数
    public static CommandLine parseCmdLine(final String appName, String[] args, Options options,
        CommandLineParser parser) {
        HelpFormatter hf = new HelpFormatter(); // 用于格式化和打印帮助信息
        hf.setWidth(110);
        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args);
            if (commandLine.hasOption('h')) { // 如果包含h选项，则打印帮助信息，并退出程序
                hf.printHelp(appName, options, true);
                System.exit(0);
            }
        } catch (ParseException e) {
            hf.printHelp(appName, options, true);
            System.exit(1);
        }

        return commandLine;
    }

    public static void printCommandLineHelp(final String appName, final Options options) {
        HelpFormatter hf = new HelpFormatter();
        hf.setWidth(110);
        hf.printHelp(appName, options, true);
    }

    // 将命令行参数转换为Properties对象
    public static Properties commandLine2Properties(final CommandLine commandLine) {
        Properties properties = new Properties();
        Option[] opts = commandLine.getOptions();

        if (opts != null) {
            for (Option opt : opts) {
                String name = opt.getLongOpt();
                String value = commandLine.getOptionValue(name);
                if (value != null) {
                    properties.setProperty(name, value);
                }
            }
        }

        return properties;
    }

}
