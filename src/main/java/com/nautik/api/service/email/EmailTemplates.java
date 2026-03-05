package com.nautik.api.service.email;

import org.springframework.stereotype.Service;

@Service
public class EmailTemplates {

    public static String verificationTemplate(String to, String fistName,String verificationUrl){
        return
                "<!DOCTYPE html>\n" +
                        "<html lang=\"es\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>Verifica tu correo - Nautik</title>\n" +
                        "    <style>\n" +
                        "      /\n" +
                        "        .ExternalClass, .ReadMsgBody { width: 100%; background-color: #f4f4f7; }\n" +
                        "        body, table, td, p, a { -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; }\n" +
                        "        table, td { border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; }\n" +
                        "        img { border: 0; height: auto; line-height: 100%; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; }\n" +
                        "        body { margin: 0; padding: 0; background-color: #f4f4f7; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body style=\"margin: 0; padding: 0; background-color: #f4f4f7; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;\">\n" +
                        "    <center style=\"width: 100%; table-layout: fixed;\">\n" +
                        "        <div style=\"max-width: 600px; margin: 0 auto; background-color: #ffffff;\">\n" +
                        "            <!-- Preheader (texto oculto que se ve en la bandeja de entrada) -->\n" +
                        "            <div style=\"display: none; max-height: 0; overflow: hidden; line-height: 1px; color: #ffffff;\">\n" +
                        "                Confirma tu dirección de correo para empezar a usar Nautik.\n" +
                        "            </div>\n" +
                        "\n" +
                        "         \n" +
                        "            <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color: #1e2b4f; border-radius: 8px 8px 0 0;\">\n" +
                        "                <tr>\n" +
                        "                    <td align=\"center\" style=\"padding: 30px 20px;\">\n" +
                        "                        <img src=\"https://nautik.app/logo-blanco.png\" alt=\"Nautik\" width=\"150\" style=\"display: block; width: 150px; max-width: 150px; height: auto;\" />\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "            </table>\n" +
                        "\n" +
                        "            \n" +
                        "            <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color: #ffffff; border-radius: 0 0 8px 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.05);\">\n" +
                        "                <tr>\n" +
                        "                    <td align=\"center\" style=\"padding: 40px 30px;\">\n" +
                        "                        <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                        "                            <tr>\n" +
                        "                                <td align=\"center\" style=\"padding-bottom: 20px;\">\n" +
                        "                                    <h1 style=\"color: #1e2b4f; font-size: 28px; font-weight: 400; margin: 0;\">¡Bienvenido a Nautik!</h1>\n" +
                        "                                </td>\n" +
                        "                            </tr>\n" +
                        "                            <tr>\n" +
                        "                                <td align=\"center\" style=\"padding-bottom: 30px;\">\n" +
                        "                                    <p style=\"color: #5e6c84; font-size: 16px; line-height: 1.6; margin: 0;\">\n" +
                        "                                        Hola <strong>"+fistName+"</strong>,<br><br>\n" +
                        "                                        Gracias por registrarte en Nautik. Para completar el proceso, por favor verifica tu dirección de correo electrónico haciendo clic en el siguiente botón:\n" +
                        "                                    </p>\n" +
                        "                                </td>\n" +
                        "                            </tr>\n" +
                        "                            <tr>\n" +
                        "                                <td align=\"center\" style=\"padding-bottom: 40px;\">\n" +
                        "                                    <a href="+verificationUrl+" target=\"_blank\" style=\"background-color: #1e2b4f; color: #ffffff; padding: 14px 40px; border-radius: 50px; text-decoration: none; font-weight: bold; font-size: 16px; display: inline-block; box-shadow: 0 4px 6px rgba(0,0,0,0.1);\">Verificar correo</a>\n" +
                        "                                </td>\n" +
                        "                            </tr>\n" +
                        "                            <tr>\n" +
                        "                                <td align=\"center\" style=\"padding-bottom: 20px;\">\n" +
                        "                                    <p style=\"color: #5e6c84; font-size: 14px; line-height: 1.5; margin: 0;\">\n" +
                        "                                        Si el botón no funciona, copia y pega el siguiente enlace en tu navegador:<br>\n" +
                        "                                        <span style=\"color: #1e2b4f; word-break: break-all;\">"+verificationUrl+"</span>\n" +
                        "                                    </p>\n" +
                        "                                </td>\n" +
                        "                            </tr>\n" +
                        "                            <tr>\n" +
                        "                                <td align=\"center\" style=\"padding-bottom: 10px;\">\n" +
                        "                                    <p style=\"color: #a0aec0; font-size: 13px; line-height: 1.5; margin: 0;\">\n" +
                        "                                        Este enlace expirará en 24 horas. Si no solicitaste esta verificación, ignora este mensaje.\n" +
                        "                                    </p>\n" +
                        "                                </td>\n" +
                        "                            </tr>\n" +
                        "                        </table>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "            </table>\n" +
                        "\n" +
                        "           \n" +
                        "            <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color: #f4f4f7; border-radius: 0 0 8px 8px;\">\n" +
                        "                <tr>\n" +
                        "                    <td align=\"center\" style=\"padding: 30px 20px;\">\n" +
                        "                        <p style=\"color: #8e9aaf; font-size: 12px; line-height: 1.5; margin: 0;\">\n" +
                        "                            © 2025 Nautik. Todos los derechos reservados.<br>\n" +
                        "                            Navegando juntos hacia nuevas experiencias.\n" +
                        "                        </p>\n" +
                        "                    </td>\n" +
                        "                </tr>\n" +
                        "            </table>\n" +
                        "        </div>\n" +
                        "    </center>\n" +
                        "</body>\n" +
                        "</html>";

    }

}
