**GPG Keys - Guide**

**Steps with Links:**

1. **Prepare Passphrase:**
    - Generate a strong passphrase using a password manager.
    - Example guide: [How To Use GPG to Encrypt and Sign Messages](https://www.digitalocean.com/community/tutorials/how-to-use-gpg-to-encrypt-and-sign-messages)

2. **Generate & Distribute GPG Key:**
    - Follow GitHub's recommended guide for creating a GPG key.
    - Guide: [Generating a new GPG key](https://docs.github.com/en/authentication/managing-commit-signature-verification/generating-a-new-gpg-key)
    - Distribute the GPG key to key servers:
        - [Ubuntu Key Server](https://keyserver.ubuntu.com)
        - [OpenPGP Key Server](https://keys.openpgp.org)
        - [MIT Key Server](https://pgp.mit.edu)
        - [Keyoxide Key Server](https://keyoxide.org)

3. **Verify Public Key:**
    - Check the published GPG keys on the chosen key server.

4. **Back Up Passphrase and Private Key:**
    - Backup procedures guide: [Backup and Restore a GPG Key](https://www.jwillikers.com/backup-and-restore-a-gpg-key)

5. **Export GPG Private Key:**
    - Export the GPG private key for Gradle configuration.
    - Note for GnuPG v2.1 or later: Create the `secring.gpg` file if it doesn't exist.
    - Terminal command for creating `secring.gpg`:
      ```
      gpg --export-secret-keys >~/.gnupg/secring.gpg
      ```
    - Ensure the migration of secret keys is successful.

By following these steps, users can effectively sign their artefacts using GPG, ensuring authenticity and security in their transmissions.